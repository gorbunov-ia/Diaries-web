import { TestBed, inject } from '@angular/core/testing';

import { NoteElementService } from './note-element.service';

describe('NoteElementService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NoteElementService]
    });
  });

  it('should be created', inject([NoteElementService], (service: NoteElementService) => {
    expect(service).toBeTruthy();
  }));
});
