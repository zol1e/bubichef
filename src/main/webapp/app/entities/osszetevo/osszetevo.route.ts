import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Osszetevo } from 'app/shared/model/osszetevo.model';
import { OsszetevoService } from './osszetevo.service';
import { OsszetevoComponent } from './osszetevo.component';
import { OsszetevoDetailComponent } from './osszetevo-detail.component';
import { OsszetevoUpdateComponent } from './osszetevo-update.component';
import { OsszetevoDeletePopupComponent } from './osszetevo-delete-dialog.component';
import { IOsszetevo } from 'app/shared/model/osszetevo.model';

@Injectable({ providedIn: 'root' })
export class OsszetevoResolve implements Resolve<IOsszetevo> {
    constructor(private service: OsszetevoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Osszetevo> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Osszetevo>) => response.ok),
                map((osszetevo: HttpResponse<Osszetevo>) => osszetevo.body)
            );
        }
        return of(new Osszetevo());
    }
}

export const osszetevoRoute: Routes = [
    {
        path: 'osszetevo',
        component: OsszetevoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bubichefApp.osszetevo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'osszetevo/:id/view',
        component: OsszetevoDetailComponent,
        resolve: {
            osszetevo: OsszetevoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bubichefApp.osszetevo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'osszetevo/new',
        component: OsszetevoUpdateComponent,
        resolve: {
            osszetevo: OsszetevoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bubichefApp.osszetevo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'osszetevo/:id/edit',
        component: OsszetevoUpdateComponent,
        resolve: {
            osszetevo: OsszetevoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bubichefApp.osszetevo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const osszetevoPopupRoute: Routes = [
    {
        path: 'osszetevo/:id/delete',
        component: OsszetevoDeletePopupComponent,
        resolve: {
            osszetevo: OsszetevoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bubichefApp.osszetevo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
