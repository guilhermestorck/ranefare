import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: '/home'
  },
  {
    path: 'home',
    loadChildren: './home/home.module#HomeModule'
  },
  {
    path:'solicitation',
    loadChildren:'./solicitation/solicitation.module#SolicitationModule'
  },
  {
    path:'plans',
    loadChildren:'./plans-page-container/plans-page-container.module#PlansPageContainerModule'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
