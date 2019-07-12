import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PlansPageContainerComponent } from './plans-page-container.component';

const routes: Routes = [

    {
        path: '',
        component: PlansPageContainerComponent
    }
];

@NgModule({
    declarations: [],
    imports: [
        RouterModule.forChild(routes)
    ],
    exports: [RouterModule]
})
export class PlansPageContainerRoutingModule { }
