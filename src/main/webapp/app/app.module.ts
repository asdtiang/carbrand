import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { CarbrandSharedModule } from 'app/shared/shared.module';
import { CarbrandCoreModule } from 'app/core/core.module';
import { CarbrandAppRoutingModule } from './app-routing.module';
import { CarbrandHomeModule } from './home/home.module';
import { CarbrandEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    CarbrandSharedModule,
    CarbrandCoreModule,
    CarbrandHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    CarbrandEntityModule,
    CarbrandAppRoutingModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [JhiMainComponent]
})
export class CarbrandAppModule {}
