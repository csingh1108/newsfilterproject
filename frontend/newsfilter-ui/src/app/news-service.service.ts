import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {NewsDto} from "./news-dto";

@Injectable({
  providedIn: 'root'
})
export class NewsServiceService {

  constructor(private httpClient: HttpClient) { }

  getNews(category: string){
    const url = `http://localhost:8080/api/news/headlines?sourceName=${category}`;
    return this.httpClient.get<Array<NewsDto>>(url);
  }

  getResults(search: string, dateFrom: Date | string, dateTo: Date | string, selected: string) {
    const surl = `http://localhost:8080/api/news/search?searchName=${search}&oldestDate=${dateFrom}&newestDate=${dateTo}&sortBy=${selected}`;
    return this.httpClient.get<Array<NewsDto>>(surl);
  }
}
