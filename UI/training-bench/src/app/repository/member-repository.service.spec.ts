import { TestBed } from '@angular/core/testing';

import { MemberRepositoryService } from './member-repository.service';

describe('MemberRepositoryService', () => {
  let service: MemberRepositoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MemberRepositoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
