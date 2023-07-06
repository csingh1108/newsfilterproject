import {Component, OnInit} from '@angular/core';
import {NewsDto} from "../news-dto";
import {NewsServiceService} from "../news-service.service";
import {SearchService} from "../search.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-resultspage',
  templateUrl: './resultspage.component.html',
  styleUrls: ['./resultspage.component.css']
})
export class ResultspageComponent implements OnInit {

  newsDto: NewsDto[] = [];
  // @ts-ignore
  // Search input
  searchData: string;
  //Capture sorting option
  selected: string = 'relevancy';

  dateFrom: Date | string = '';
  dateTo: Date | string = '';

  areOptionsVisible: boolean = false;


  constructor(private newsService: NewsServiceService, private searchService: SearchService,
              private router: Router){

  }
  ngOnInit(): void {
    this.searchService.valueTransfer$.subscribe((data) => {
      this.searchData= data;
      this.updateResults(this.searchData);
    })
  }

  updateResults(search: string) {
    const formattedDateFrom = (typeof this.dateFrom === 'string') ? this.dateFrom : this.dateFrom.toISOString();
    const formattedDateTo = (typeof this.dateTo === 'string') ? this.dateTo : this.dateTo.toISOString();
    this.newsService.getResults(search, formattedDateFrom, formattedDateTo, this.selected)
      .subscribe(response => {
        this.newsDto= response;

      })
  }

  goToHome() {
    this.router.navigateByUrl('/news');
  }

  // Check if date is within last 30 days
  myFilter = (d: Date | null): boolean => {
    const currentDate = new Date();
    const selectedDate = d || new Date();

    const timeDiff = currentDate.getTime() - selectedDate.getTime();

    const daysDiff = timeDiff / (1000 * 3600 * 24);

    // Prevent Saturday and Sunday from being selected.
    return daysDiff >= 1 && daysDiff <= 30;
  };

  clearDateSelection(){
    this.dateFrom = '';
    this.dateTo = '';
  }

  showOptions() {
    this.areOptionsVisible = !this.areOptionsVisible;
  }
}
