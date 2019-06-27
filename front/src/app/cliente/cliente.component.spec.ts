import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClienteComponent } from './cliente.component';
import { ClienteService } from '../util/service/cliente.service';
import { IConfig, NgxMaskModule } from 'ngx-mask';
import { MatCardModule, MatFormFieldModule, MatInputModule, MatSelectModule, MatDividerModule, MatListModule, MatButtonModule, MatTabsModule, MatTableModule, MatMenuModule, MatIconModule } from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ToastrModule } from 'ngx-toastr';
import { RouterLink } from '@angular/router';

export let options: Partial<IConfig> | (() => Partial<IConfig>);

describe('ClienteComponent', () => {
  let component: ClienteComponent;
  let fixture: ComponentFixture<ClienteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
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
      ],
      declarations: [ClienteComponent],
      providers: [ClienteService, RouterLink]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClienteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Verify title', () => {
    let titleCard = fixture.nativeElement.querySelector('mat-card-title');
    expect(titleCard.innerText).toEqual('Listagem de Cliente', 'Deveria possuir o titulo Listagem de Cliente');
  });

  it('Check the table header, the first item should be Nome', () => {
    let table = fixture.nativeElement.querySelector('table');
    expect(table.tHead.rows[0].cells[0].innerText).toEqual('Nome', 'First column should be filled with Nome')
  })

  it('Check the table header, the second item should be E-mail', () => {
    let table = fixture.nativeElement.querySelector('table');
    expect(table.tHead.rows[0].cells[1].innerText).toEqual('E-mail', 'Second column should be filled with E-mail')
  })

  it('Check the table header, the third item should be Ação', () => {
    let table = fixture.nativeElement.querySelector('table');
    expect(table.tHead.rows[0].cells[2].innerText).toEqual('Ação', 'Third column should be filled with Ação')
  })
});
