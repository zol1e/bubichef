import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ReceptToOsszetevo } from 'app/shared/model/recept-to-osszetevo.model';
import { ReceptToOsszetevoService } from './recept-to-osszetevo.service';
import { ReceptToOsszetevoComponent } from './recept-to-osszetevo.component';
import { ReceptToOsszetevoDetailComponent } from './recept-to-osszetevo-detail.component';
import { ReceptToOsszetevoUpdateComponent } from './recept-to-osszetevo-update.component';
import { ReceptToOsszetevoDeletePopupComponent } from './recept-to-osszetevo-delete-dialog.component';
import { IReceptToOsszetevo } from 'app/shared/model/recept-to-osszetevo.model';

@Injectable({ providedIn: 'root' })
export class ReceptToOsszetevoResolve implements Resolve<IReceptToOsszetevo> {
    constructor(private service: ReceptToOsszetevoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ReceptToOsszetevo> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ReceptToOsszetevo>) => response.ok),
                map((receptToOsszetevo: HttpResponse<ReceptToOsszetevo>) => receptToOsszetevo.body)
            );
        }
        return of(new ReceptToOsszetevo());
    }
}

export const receptToOsszetevoRoute: Routes = [
    {
        path: 'recept-to-osszetevo',
        component: ReceptToOsszetevoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bubichefApp.receptToOsszetevo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recept-to-osszetevo/:id/view',
        component: ReceptToOsszetevoDetailComponent,
        resolve: {
            receptToOsszetevo: ReceptToOsszetevoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bubichefApp.receptToOsszetevo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recept-to-osszetevo/new',
        component: ReceptToOsszetevoUpdateComponent,
        resolve: {
            receptToOsszetevo: ReceptToOsszetevoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bubichefApp.receptToOsszetevo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recept-to-osszetevo/:id/edit',
        component: ReceptToOsszetevoUpdateComponent,
        resolve: {
            receptToOsszetevo: ReceptToOsszetevoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bubichefApp.receptToOsszetevo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const receptToOsszetevoPopupRoute: Routes = [
    {
        path: 'recept-to-osszetevo/:id/delete',
        component: ReceptToOsszetevoDeletePopupComponent,
        resolve: {
            receptToOsszetevo: ReceptToOsszetevoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bubichefApp.receptToOsszetevo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
