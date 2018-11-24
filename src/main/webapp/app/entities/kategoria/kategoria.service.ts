import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IKategoria } from 'app/shared/model/kategoria.model';

type EntityResponseType = HttpResponse<IKategoria>;
type EntityArrayResponseType = HttpResponse<IKategoria[]>;

@Injectable({ providedIn: 'root' })
export class KategoriaService {
    public resourceUrl = SERVER_API_URL + 'api/kategorias';

    constructor(private http: HttpClient) {}

    create(kategoria: IKategoria): Observable<EntityResponseType> {
        return this.http.post<IKategoria>(this.resourceUrl, kategoria, { observe: 'response' });
    }

    update(kategoria: IKategoria): Observable<EntityResponseType> {
        return this.http.put<IKategoria>(this.resourceUrl, kategoria, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IKategoria>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IKategoria[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
