import { TestBed } from '@angular/core/testing';
import { ResolveFn } from '@angular/router';

import { resultspageResolver } from './resultspage.resolver';

describe('resultspageResolver', () => {
  const executeResolver: ResolveFn<boolean> = (...resolverParameters) => 
      TestBed.runInInjectionContext(() => resultspageResolver(...resolverParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeResolver).toBeTruthy();
  });
});
