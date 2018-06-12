import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

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

  note: Note = new Note();
  notesElements: NoteElement[];

  editedNoteElement: NoteElement;
  isFormOpen: boolean;

  constructor(
    private route: ActivatedRoute,
    private noteElementService: NoteElementService,
    private noteService: NoteService,
    ) { }

  ngOnInit() {
    this.getNote();
    this.getNoteElements();
  }

  getNoteElements(): void {
    const noteId = +this.route.snapshot.paramMap.get('noteId');
    this.noteElementService.getNoteElements(noteId)
      .subscribe(notesElements => this.notesElements = notesElements);
  }

  getNote(): void {
    const noteId = +this.route.snapshot.paramMap.get('noteId');
    this.noteService.getNote(noteId)
      .subscribe(note => this.note = note);
  }

  swap(noteElementId: number, swapType: String): void {
    this.noteElementService.swap(noteElementId, this.getSortBy(noteElementId, swapType))
      .subscribe(elements => {
        elements.forEach(element => this.updateNoteElement(element));
      });
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

  private updateNoteElement(element: NoteElement): void {
    for (let i = 0; i < this.notesElements.length; i++) {
      if (element.sortBy === this.notesElements[i].sortBy) {
        this.notesElements[i].id = element.id;
        this.notesElements[i].description = element.description;
        this.notesElements[i].lastModified = element.lastModified;
        return;
      }
    }
  }

  initEditForm(noteElement: NoteElement) {
    this.editedNoteElement = noteElement;
    this.isFormOpen = true;
  }

  deleteNoteElement(noteElement: NoteElement): void {
    this.noteElementService.deleteNoteElement(noteElement.id)
      .subscribe(_ => {
          const index = this.notesElements.indexOf(noteElement);
          this.notesElements.splice(index, 1);
      });
  }

  actualizeListOfNotesElements(noteElement: NoteElement) {
    if (this.editedNoteElement) {
      for (let i = 0; i < this.notesElements.length; i++) {
        if (this.notesElements[i].id === noteElement.id) {
          this.notesElements[i] = noteElement;
          break;
        }
      }
    } else {
      this.notesElements.push(noteElement);
    }
    this.sortNotesElements();
  }

  private sortNotesElements(): void {
    this.notesElements.sort((a, b) => b.sortBy - a.sortBy);
  }

}
