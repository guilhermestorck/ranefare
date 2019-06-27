import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule, MatCardModule, MatCheckboxModule, MatDividerModule, MatFormFieldModule, MatIconModule, MatInputModule, MatListModule, MatMenuModule, MatSelectModule, MatTableModule, MatTabsModule } from '@angular/material';
import { IConfig, NgxMaskModule } from 'ngx-mask';
import { ToastrModule } from 'ngx-toastr';
import { CidadeService } from '../util/service/cidade.service';
import { ClienteService } from '../util/service/cliente.service';
import { CadastroClienteComponent } from './cadastro-cliente/cadastro-cliente.component';
import { ClienteComponent } from './cliente.component';
import { ClienteRoutingModule } from './cliente.routing.module';
import { RouterModule } from '@angular/router';

export let options: Partial<IConfig> | (() => Partial<IConfig>);

@NgModule({
    declarations: [
        ClienteComponent,
        CadastroClienteComponent,
    ],
    imports: [
        CommonModule,
        ClienteRoutingModule,
        FormsModule,
        MatCheckboxModule,
        MatCardModule,
        MatFormFieldModule,
        MatInputModule,
        MatSelectModule,
        MatDividerModule,
        MatListModule,
        MatButtonModule,
        MatTabsModule,
        HttpClientModule,
        ToastrModule.forRoot({
            timeOut: 15000
        }),
        MatTableModule,
        MatMenuModule,
        MatIconModule,
        ReactiveFormsModule,
        NgxMaskModule.forRoot(options),
        // RouterModule
    ],
    providers: [ClienteService, CidadeService],
    // exports: [RouterModule],
})
export class ClienteModule { }
