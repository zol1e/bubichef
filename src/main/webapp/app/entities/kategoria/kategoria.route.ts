import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Kategoria } from 'app/shared/model/kategoria.model';
import { KategoriaService } from './kategoria.service';
import { KategoriaComponent } from './kategoria.component';
import { KategoriaDetailComponent } from './kategoria-detail.component';
import { KategoriaUpdateComponent } from './kategoria-update.component';
import { KategoriaDeletePopupComponent } from './kategoria-delete-dialog.component';
import { IKategoria } from 'app/shared/model/kategoria.model';

@Injectable({ providedIn: 'root' })
export class KategoriaResolve implements Resolve<IKategoria> {
    constructor(private service: KategoriaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Kategoria> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Kategoria>) => response.ok),
                map((kategoria: HttpResponse<Kategoria>) => kategoria.body)
            );
        }
        return of(new Kategoria());
    }
}

export const kategoriaRoute: Routes = [
    {
        path: 'kategoria',
        component: KategoriaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bubichefApp.kategoria.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'kategoria/:id/view',
        component: KategoriaDetailComponent,
        resolve: {
            kategoria: KategoriaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bubichefApp.kategoria.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'kategoria/new',
        component: KategoriaUpdateComponent,
        resolve: {
            kategoria: KategoriaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bubichefApp.kategoria.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'kategoria/:id/edit',
        component: KategoriaUpdateComponent,
        resolve: {
            kategoria: KategoriaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bubichefApp.kategoria.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const kategoriaPopupRoute: Routes = [
    {
        path: 'kategoria/:id/delete',
        component: KategoriaDeletePopupComponent,
        resolve: {
            kategoria: KategoriaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bubichefApp.kategoria.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
