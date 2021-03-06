import { BrowserModule, HAMMER_GESTURE_CONFIG } from '@angular/platform-browser';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AppComponent } from './app.component';
import { CoreModule } from './core/core.module';
import { HomeModule } from './home/home.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { GestureConfig, MatTabsModule, MatTable, MatTableModule } from '@angular/material';
import { RentDialogComponent } from './components/rent-dialog/rent-dialog.component';
import { MatButtonModule } from '@angular/material/button';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { FormsModule } from '@angular/forms';
import { CartDialogComponent } from './components/cart-dialog/cart-dialog.component';
import { MatListModule, MatListItem } from '@angular/material/list';
import { ReportDialogComponent } from './components/report-dialog/report-dialog.component';
import { AgmCoreModule } from '@agm/core';
import { MapDialogComponent } from './components/map-dialog/map-dialog.component';
import { MyVehiclesReportDialogComponent } from './components/my-vehicles/my-vehicles-report-dialog/my-vehicles-report-dialog.component';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    CoreModule,
    HomeModule,
    BrowserAnimationsModule,
    AppRoutingModule
  ],
  providers: [{ provide: HAMMER_GESTURE_CONFIG, useClass: GestureConfig }],
  bootstrap: [AppComponent],
  exports: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AppModule { }
