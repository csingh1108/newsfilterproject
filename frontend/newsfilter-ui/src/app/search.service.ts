import { Injectable } from '@angular/core';
import { Observable, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  // Used to transfer search value between components
  valueTransfer$: Observable<any>;
  private valueTransferSubject = new Subject<string>();

  constructor() {
    this.valueTransfer$ = this.valueTransferSubject.asObservable();
  }

  // @ts-ignore
  valueTransfer(data) {
    this.valueTransferSubject.next(data);
  }
}
