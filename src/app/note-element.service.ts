import { Injectable } from '@angular/core';
import { NoteElement } from './note-element';
import { ELEMENTS } from './mock-note-elements';

@Injectable()
export class NoteElementService {

  constructor() { }

  getNoteElements(noteId: number): NoteElement[] {
    return ELEMENTS;
  }

}
