import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRecept } from 'app/shared/model/recept.model';

type EntityResponseType = HttpResponse<IRecept>;
type EntityArrayResponseType = HttpResponse<IRecept[]>;

@Injectable({ providedIn: 'root' })
export class ReceptService {
    public resourceUrl = SERVER_API_URL + 'api/recepts';

    constructor(private http: HttpClient) {}

    create(recept: IRecept): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(recept);
        return this.http
            .post<IRecept>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(recept: IRecept): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(recept);
        return this.http
            .put<IRecept>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRecept>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRecept[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(recept: IRecept): IRecept {
        const copy: IRecept = Object.assign({}, recept, {
            feltoltve: recept.feltoltve != null && recept.feltoltve.isValid() ? recept.feltoltve.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.feltoltve = res.body.feltoltve != null ? moment(res.body.feltoltve) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((recept: IRecept) => {
                recept.feltoltve = recept.feltoltve != null ? moment(recept.feltoltve) : null;
            });
        }
        return res;
    }
}
