import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainNewsComponent} from "./main-news/main-news.component";
import {ResultspageComponent} from "./resultspage/resultspage.component";
import {SearchBarComponent} from "./search-bar/search-bar.component";

const routes: Routes = [
  {
    path:'', redirectTo: 'news', pathMatch: 'full'
  },
  {
    path: 'news', component: MainNewsComponent
  },
  {
    path: 'results', component: ResultspageComponent
  },
  {
    path: 'search', component: SearchBarComponent
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
