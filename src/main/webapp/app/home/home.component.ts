import { Component, OnInit } from '@angular/core';
import { NgbModalRef, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LoginModalService, Principal, Account, StateStorageService, LoginService } from 'app/core';
import { Router } from '@angular/router';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.css']
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    password: string;
    authenticationError: boolean;

    constructor(
            private principal: Principal, 
            private loginModalService: LoginModalService, 
            private eventManager: JhiEventManager,
            private loginService: LoginService,
            private stateStorageService: StateStorageService,
            private router: Router
    ) {}

    ngOnInit() {
        this.principal.identity().then(account => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.principal.identity().then(account => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        //this.modalRef = this.loginModalService.open();
        this.loginService
        .login({
            username: "user",
            password: this.password,
            rememberMe: true
        })
        .then(() => {
            this.authenticationError = false;
            if (this.router.url === '/register' || /^\/activate\//.test(this.router.url) || /^\/reset\//.test(this.router.url)) {
                this.router.navigate(['']);
            }

            this.eventManager.broadcast({
                name: 'authenticationSuccess',
                content: 'Sending Authentication Success'
            });

            // previousState was set in the authExpiredInterceptor before being redirected to login modal.
            // since login is succesful, go to stored previousState and clear previousState
            const redirect = this.stateStorageService.getUrl();
            if (redirect) {
                this.stateStorageService.storeUrl(null);
                this.router.navigate([redirect]);
            }
        })
        .catch(() => {
            this.authenticationError = true;
        });
    }
}
