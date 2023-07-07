import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class NavigationService {

  //Used to transfer category value between components
  valueTransfer$: Observable<any>;
  private valueTransferSubject = new BehaviorSubject<string>('general');

  constructor() {
    this.valueTransfer$ = this.valueTransferSubject.asObservable();
  }

  // @ts-ignore
  valueTransfer(data) {
    this.valueTransferSubject.next(data);
  }
}
