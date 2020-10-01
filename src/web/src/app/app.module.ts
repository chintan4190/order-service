import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CreateComponent} from './components/order/create/create.component';
import {ConfirmComponent} from './components/order/confirm/confirm.component';
import {FormsModule} from "@angular/forms";
import {MainComponent} from './components/main/main.component';
import {ViewComponent} from './components/order/view/view.component';
import {HttpClientModule} from "@angular/common/http";
import { CancelComponent } from './components/order/cancel/cancel.component';

@NgModule({
  declarations: [
    AppComponent,
    CreateComponent,
    ConfirmComponent,
    MainComponent,
    ViewComponent,
    CancelComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
