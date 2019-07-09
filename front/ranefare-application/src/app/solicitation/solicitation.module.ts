import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FipeService } from '../util/service/fipe.service';
import { SolicitationComponent } from './solicitation.component';
import { SolicitationRoutingModule } from './solicitation.routing.module';
import {NgxMaskModule, IConfig} from 'ngx-mask'

export let options: Partial<IConfig> | (() => Partial<IConfig>);

@NgModule({
    declarations: [
        SolicitationComponent
    ],
    imports: [
        CommonModule,
        SolicitationRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        NgxMaskModule.forRoot(options)
    ],
    providers: [
        FipeService
    ],
})
export class SolicitationModule { }
