import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { IConfig } from 'ngx-mask';
import { PlansPageContainerComponent } from './plans-page-container.component';
import { PlansPageContainerRoutingModule } from './plans-page-container.routing.module';
import { PlanPanelComponent } from '../plan-panel/plan-panel.component';

export let options: Partial<IConfig> | (() => Partial<IConfig>);

@NgModule({
    declarations: [
        PlansPageContainerComponent,
        PlanPanelComponent
    ],
    imports: [
        CommonModule,
        PlansPageContainerRoutingModule,
    ],
    providers: [
    ],
})
export class PlansPageContainerModule { }
