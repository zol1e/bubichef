import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HashTag } from 'app/shared/model/hash-tag.model';
import { HashTagService } from './hash-tag.service';
import { HashTagComponent } from './hash-tag.component';
import { HashTagDetailComponent } from './hash-tag-detail.component';
import { HashTagUpdateComponent } from './hash-tag-update.component';
import { HashTagDeletePopupComponent } from './hash-tag-delete-dialog.component';
import { IHashTag } from 'app/shared/model/hash-tag.model';

@Injectable({ providedIn: 'root' })
export class HashTagResolve implements Resolve<IHashTag> {
    constructor(private service: HashTagService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<HashTag> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<HashTag>) => response.ok),
                map((hashTag: HttpResponse<HashTag>) => hashTag.body)
            );
        }
        return of(new HashTag());
    }
}

export const hashTagRoute: Routes = [
    {
        path: 'hash-tag',
        component: HashTagComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bubichefApp.hashTag.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hash-tag/:id/view',
        component: HashTagDetailComponent,
        resolve: {
            hashTag: HashTagResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bubichefApp.hashTag.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hash-tag/new',
        component: HashTagUpdateComponent,
        resolve: {
            hashTag: HashTagResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bubichefApp.hashTag.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hash-tag/:id/edit',
        component: HashTagUpdateComponent,
        resolve: {
            hashTag: HashTagResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bubichefApp.hashTag.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hashTagPopupRoute: Routes = [
    {
        path: 'hash-tag/:id/delete',
        component: HashTagDeletePopupComponent,
        resolve: {
            hashTag: HashTagResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bubichefApp.hashTag.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
