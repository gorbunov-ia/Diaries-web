import { TestBed } from '@angular/core/testing';

import { NoteElementService } from './note-element.service';

describe('NoteElementService', () => {
  let service: NoteElementService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NoteElementService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
