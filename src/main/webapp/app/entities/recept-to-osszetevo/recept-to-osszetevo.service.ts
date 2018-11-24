import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReceptToOsszetevo } from 'app/shared/model/recept-to-osszetevo.model';

type EntityResponseType = HttpResponse<IReceptToOsszetevo>;
type EntityArrayResponseType = HttpResponse<IReceptToOsszetevo[]>;

@Injectable({ providedIn: 'root' })
export class ReceptToOsszetevoService {
    public resourceUrl = SERVER_API_URL + 'api/recept-to-osszetevos';

    constructor(private http: HttpClient) {}

    create(receptToOsszetevo: IReceptToOsszetevo): Observable<EntityResponseType> {
        return this.http.post<IReceptToOsszetevo>(this.resourceUrl, receptToOsszetevo, { observe: 'response' });
    }

    update(receptToOsszetevo: IReceptToOsszetevo): Observable<EntityResponseType> {
        return this.http.put<IReceptToOsszetevo>(this.resourceUrl, receptToOsszetevo, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IReceptToOsszetevo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IReceptToOsszetevo[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
