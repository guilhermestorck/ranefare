import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClienteComponent } from './cliente.component';
import { CadastroClienteComponent } from './cadastro-cliente/cadastro-cliente.component';

const routes: Routes = [

    {
        path: '',
        component: ClienteComponent
    },
    {
        path: 'novo',
        component: CadastroClienteComponent
    },
    {
        path: 'editar/:id',
        component: CadastroClienteComponent
    }
];

@NgModule({
    declarations: [],
    imports: [
        RouterModule.forChild(routes)
    ],
    exports: [RouterModule]
})
export class ClienteRoutingModule { }
