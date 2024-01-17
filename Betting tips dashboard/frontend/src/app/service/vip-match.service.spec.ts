import { TestBed } from '@angular/core/testing';

import { VipMatchService } from './vip-match.service';

describe('VipMatchService', () => {
  let service: VipMatchService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VipMatchService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
