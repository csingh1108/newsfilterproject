import {Component, OnInit} from '@angular/core';
import {NewsServiceService} from "../news-service.service";
import {NewsDto} from "../news-dto";
import {NavigationService} from "../navigation.service";

@Component({
  selector: 'app-main-news',
  templateUrl: './main-news.component.html',
  styleUrls: ['./main-news.component.css']
})
export class MainNewsComponent implements OnInit{

  newsDto: NewsDto[] = [];
  // @ts-ignore
  data: string;

  constructor(private newsService: NewsServiceService, private navService:NavigationService) {

  }


  ngOnInit(): void {
    this.navService.valueTransfer$.subscribe((data) => {
      this.data= data;
      this.updateNews(this.data);
      })
  }

  updateNews(category: string) {
    this.newsService.getNews(category)
      .subscribe(response => {
        this.newsDto= response;
      })
  }

}
