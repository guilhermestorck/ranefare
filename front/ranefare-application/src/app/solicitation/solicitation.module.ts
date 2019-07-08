import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FipeService } from '../util/service/fipe.service';
import { SolicitationComponent } from './solicitation.component';
import { SolicitationRoutingModule } from './solicitation.routing.module';

@NgModule({
    declarations: [
        SolicitationComponent
    ],
    imports: [
        CommonModule,
        SolicitationRoutingModule,
    ],
    providers: [
        FipeService
    ],
})
export class SolicitationModule { }
