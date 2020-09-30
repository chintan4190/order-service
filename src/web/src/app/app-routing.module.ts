import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CreateComponent} from "./components/order/create/create.component";
import {ConfirmComponent} from "./components/order/confirm/confirm.component";
import {MainComponent} from "./components/main/main.component";
import {ViewComponent} from "./components/order/view/view.component";
import {CancelComponent} from "./components/order/cancel/cancel.component";


const routes: Routes = [
  {path: '', component: MainComponent},
  {path: 'create', component: CreateComponent},
  {path: 'confirm/:id', component: ConfirmComponent},
  {path: 'cancel/:id', component: CancelComponent},
  {path: 'view/:id', component: ViewComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
