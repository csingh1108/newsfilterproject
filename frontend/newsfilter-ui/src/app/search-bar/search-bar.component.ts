import {Component, ViewEncapsulation} from '@angular/core';
import {SearchService} from "../search.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css'],
  encapsulation: ViewEncapsulation.None,

})
export class SearchBarComponent {

  searchValue: string = "";

  constructor(private searchService: SearchService, private router: Router){
  }

  executeSearch()
    {
      if(this.searchValue != ""){
        if(this.router.url === '/results'){
          this.searchService.valueTransfer(this.searchValue);
        } else {
          this.router.navigateByUrl('/results').then(() => {
            this.searchService.valueTransfer(this.searchValue);
          });
        }
      }
    }

  clearSearch() {
    this.searchValue = "";
  }
}
