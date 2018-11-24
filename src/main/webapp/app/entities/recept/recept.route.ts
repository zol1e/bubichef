import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Recept } from 'app/shared/model/recept.model';
import { ReceptService } from './recept.service';
import { ReceptComponent } from './recept.component';
import { ReceptDetailComponent } from './recept-detail.component';
import { ReceptUpdateComponent } from './recept-update.component';
import { ReceptDeletePopupComponent } from './recept-delete-dialog.component';
import { IRecept } from 'app/shared/model/recept.model';

@Injectable({ providedIn: 'root' })
export class ReceptResolve implements Resolve<IRecept> {
    constructor(private service: ReceptService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Recept> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Recept>) => response.ok),
                map((recept: HttpResponse<Recept>) => recept.body)
            );
        }
        return of(new Recept());
    }
}

export const receptRoute: Routes = [
    {
        path: 'recept',
        component: ReceptComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bubichefApp.recept.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recept/:id/view',
        component: ReceptDetailComponent,
        resolve: {
            recept: ReceptResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bubichefApp.recept.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recept/new',
        component: ReceptUpdateComponent,
        resolve: {
            recept: ReceptResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bubichefApp.recept.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recept/:id/edit',
        component: ReceptUpdateComponent,
        resolve: {
            recept: ReceptResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bubichefApp.recept.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const receptPopupRoute: Routes = [
    {
        path: 'recept/:id/delete',
        component: ReceptDeletePopupComponent,
        resolve: {
            recept: ReceptResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bubichefApp.recept.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
