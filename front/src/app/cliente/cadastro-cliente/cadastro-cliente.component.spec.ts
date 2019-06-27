import { HttpClientModule } from '@angular/common/http';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule, MatCardModule, MatDividerModule, MatFormFieldModule, MatIconModule, MatInputModule, MatListModule, MatMenuModule, MatSelectModule, MatTableModule, MatTabsModule } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { IConfig, NgxMaskModule } from 'ngx-mask';
import { ToastrModule } from 'ngx-toastr';
import { CidadeService } from '../../util/service/cidade.service';
import { ClienteService } from '../../util/service/cliente.service';
import { CadastroClienteComponent } from './cadastro-cliente.component';

export let options: Partial<IConfig> | (() => Partial<IConfig>);

describe('CadastroClienteComponent', () => {
  let component: CadastroClienteComponent;
  let fixture: ComponentFixture<CadastroClienteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [CadastroClienteComponent],
      imports: [
        MatCardModule,
        FormsModule,
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
        RouterTestingModule,
        BrowserAnimationsModule
      ],
      providers: [
        ClienteService,
        CidadeService,
      ],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CadastroClienteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('', () => {

  })
});
