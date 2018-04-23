import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Note } from '../note';
import { NoteElement } from '../note-element';
import { NoteElementService } from '../note-element.service';
import { NoteService } from '../note.service';

@Component({
  selector: 'app-note-element',
  templateUrl: './note-element.component.html',
  styleUrls: ['./note-element.component.css']
})
export class NoteElementComponent implements OnInit {

  note: Note;
  notesElements: NoteElement[];

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private noteElementService: NoteElementService,
    private noteService: NoteService,
    ) { }

  ngOnInit() {
    this.getNote();
    this.getNoteElements();
  }

  getNoteElements(): void {
    const noteId = +this.route.snapshot.paramMap.get('noteId');
    this.notesElements = this.noteElementService.getNoteElements(noteId);
  }

  getNote(): void {
    const noteId = +this.route.snapshot.paramMap.get('noteId');
    this.note = this.noteService.getNote(noteId);
  }

  swap(noteElementId: number, swapType: String): void {
    console.log(this.getSortBy(noteElementId, swapType));
  }

  private getSortBy(noteElementId: number, swapType: String): number {
    if (swapType === 'First' || this.notesElements.length === 1) {
      return this.notesElements[0].sortBy;
    }
    if (swapType === 'Last') {
      return this.notesElements[this.notesElements.length - 1].sortBy;
    }
    let index = 0;
    for (index = 0; index < this.notesElements.length; index++)  {
      if (this.notesElements[index].id === noteElementId) {
        break;
      }
    }
    if (swapType === 'Prev') {
      return index === 0 ? this.notesElements[0].sortBy : this.notesElements[index - 1].sortBy;
    }
    return index === this.notesElements.length - 1 ? this.notesElements[index].sortBy : this.notesElements[index + 1].sortBy;
  }
}
