import { Component, OnInit } from '@angular/core';
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
}
