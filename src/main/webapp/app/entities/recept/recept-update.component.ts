import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IRecept } from 'app/shared/model/recept.model';
import { ReceptService } from './recept.service';
import { IKategoria } from 'app/shared/model/kategoria.model';
import { KategoriaService } from 'app/entities/kategoria';
import { IHashTag } from 'app/shared/model/hash-tag.model';
import { HashTagService } from 'app/entities/hash-tag';
import { OsszetevoService } from "app/entities/osszetevo";
import { ReceptToOsszetevoService } from "app/entities/recept-to-osszetevo";
import { IReceptToOsszetevo, ReceptToOsszetevo } from "app/shared/model/recept-to-osszetevo.model";
import { IOsszetevo, Osszetevo } from "app/shared/model/osszetevo.model";

@Component({
    selector: 'jhi-recept-update',
    templateUrl: './recept-update.component.html'
})
export class ReceptUpdateComponent implements OnInit {
    recept: IRecept;
    isSaving: boolean;

    kategorias: IKategoria[];

    hashtags: IHashTag[];

    osszetevoks: IReceptToOsszetevo[];

    allOsszetevo: IOsszetevo[];

    feltoltveDp: any;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private receptService: ReceptService,
        private kategoriaService: KategoriaService,
        private hashTagService: HashTagService,
        private osszetevoMappingService: ReceptToOsszetevoService,
        private allOsszetevoService: OsszetevoService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ recept }) => {
            this.recept = recept;
        });
        this.kategoriaService.query().subscribe(
            (res: HttpResponse<IKategoria[]>) => {
                this.kategorias = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.hashTagService.query().subscribe(
            (res: HttpResponse<IHashTag[]>) => {
                this.hashtags = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.osszetevoMappingService.query().subscribe(
                (res: HttpResponse<IReceptToOsszetevo[]>) => {
                    this.osszetevoks = res.body;
                    console.log(this.osszetevoks);
                },
                (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.allOsszetevoService.query().subscribe(
                (res: HttpResponse<IOsszetevo[]>) => {
                    this.allOsszetevo = res.body;
                    console.log(this.allOsszetevo);
                },
                (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.recept, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    addOsszetevo() {
      let recept2osszetevo = new ReceptToOsszetevo(null, "", "", this.recept.id, null);

      let osszetevo = new Osszetevo(null, null, null, null, null, null);
      recept2osszetevo.osszetevo = osszetevo;

      this.recept.osszetevoks.push(recept2osszetevo);
    }

    removeOsszetevo(i: number) {
      this.recept.osszetevoks.splice(i, 1);
    }

    save() {
        this.isSaving = true;

        for (var i = this.recept.osszetevoks.length -1; i >= 0; i--) {
          if (this.recept.osszetevoks[i].osszetevo.id == null) {
              this.recept.osszetevoks.splice(i, 1);
          }
        }

        if (this.recept.id !== undefined) {
            this.subscribeToSaveResponse(this.receptService.update(this.recept));
        } else {
            this.subscribeToSaveResponse(this.receptService.create(this.recept));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRecept>>) {
        result.subscribe((res: HttpResponse<IRecept>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackKategoriaById(index: number, item: IKategoria) {
        return item.id;
    }

    trackHashTagById(index: number, item: IHashTag) {
        return item.id;
    }

    trackOsszetevokById(index: number, item: IOsszetevo) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }

    /*getOsszetevo(id: number) {
        for (let i = 0; i < this.allOsszetevo.length; i++) {
            if (id === this.allOsszetevo[i].id) {
                return this.allOsszetevo[i].nev;
            }
        }
        return "";
    }*/

    getOsszetevo(id: number) {
        for (let i = 0; i < this.recept.osszetevoks.length; i++) {
            if (id === this.recept.osszetevoks[i].id) {
                return this.recept.osszetevoks[i];
            }
        }
        return null;
    }

    selectOsszetevo(id: number, newId: number) {
      this.recept.osszetevoks[id].osszetevo.id = newId;
    }
}
