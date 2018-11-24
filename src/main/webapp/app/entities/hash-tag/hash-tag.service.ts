import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHashTag } from 'app/shared/model/hash-tag.model';

type EntityResponseType = HttpResponse<IHashTag>;
type EntityArrayResponseType = HttpResponse<IHashTag[]>;

@Injectable({ providedIn: 'root' })
export class HashTagService {
    public resourceUrl = SERVER_API_URL + 'api/hash-tags';

    constructor(private http: HttpClient) {}

    create(hashTag: IHashTag): Observable<EntityResponseType> {
        return this.http.post<IHashTag>(this.resourceUrl, hashTag, { observe: 'response' });
    }

    update(hashTag: IHashTag): Observable<EntityResponseType> {
        return this.http.put<IHashTag>(this.resourceUrl, hashTag, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IHashTag>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IHashTag[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
