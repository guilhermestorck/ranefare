import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SolicitationComponent } from './solicitation.component';

const routes: Routes = [

    {
        path: '',
        component: SolicitationComponent
    }
];

@NgModule({
    declarations: [],
    imports: [
        RouterModule.forChild(routes)
    ],
    exports: [RouterModule]
})
export class SolicitationRoutingModule { }
