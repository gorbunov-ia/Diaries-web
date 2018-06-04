import { Component, OnInit } from '@angular/core';
import { Note } from '../note';
import { NoteService } from '../note.service';

@Component({
  selector: 'app-note',
  templateUrl: './note.component.html',
  styleUrls: ['./note.component.css']
})
export class NoteComponent implements OnInit {

  notes: Note[];
  editedNote: Note;
  isNoteEditFormOpen: boolean;

  constructor(private noteService: NoteService) { }

  ngOnInit() {
    this.getNotes();
  }

  getNotes(): void {
    this.noteService.getNotes()
      .subscribe(notes => this.notes = notes);
  }

  initEditForm(note: Note) {
    this.editedNote = note;
    this.isNoteEditFormOpen = true;
  }

  deleteNote(note: Note): void {
    this.noteService.deleteNote(note.id)
      .subscribe(_ => {
          const index = this.notes.indexOf(note);
          this.notes.splice(index, 1);
      });
  }

  actualizeListOfNotes(note: Note) {
    if (this.editedNote) {
      for (let i = 0; i < this.notes.length; i++) {
        if (this.notes[i].id === note.id) {
          this.notes[i] = note;
          break;
        }
      }
    } else {
      this.notes.push(note);
    }
    this.sortNotes();
  }

  private sortNotes(): void {
    this.notes.sort((a, b) => b.sortBy - a.sortBy);
  }

}
