import { ResolveFn } from '@angular/router';
import {Injectable} from "@angular/core";


export const resultspageResolver: ResolveFn<boolean> =
  (route, state) => {
  return true;
};
