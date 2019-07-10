import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FipeService } from '../util/service/fipe.service';
import {NgxMaskModule, IConfig} from 'ngx-mask'
import { PlansRoutingModule } from './plans.routing.module';
import { PlansComponent } from './plans.component';

export let options: Partial<IConfig> | (() => Partial<IConfig>);

@NgModule({
    declarations: [
        PlansComponent
    ],
    imports: [
        CommonModule,
        PlansRoutingModule,
    ],
    providers: [
    ],
})
export class PlansModule { }
