import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Note } from '../note';
import { NoteService } from '../note.service';
import { map, catchError } from 'rxjs/operators';
import { of } from 'rxjs/observable/of';

@Component({
  selector: 'app-note',
  templateUrl: './note.component.html',
  styleUrls: ['./note.component.css']
})
export class NoteComponent implements OnInit {

  notes: Note[];
  newNote: Note;
  isNewNoteFormOpened: boolean;
  errorMsg: boolean;
  onProgress: boolean;

  constructor(private noteService: NoteService) { }

  ngOnInit() {
    this.getNotes();
    this.closeForm();
  }

  getNotes(): void {
    this.noteService.getNotes()
      .subscribe(notes => this.notes = notes);
  }

  onSubmit(form: NgForm): void {
    if (!form.valid) {
      return;
    }
    this.onProgress = true;
    this.createNote(_ => {
      form.reset();
      this.onProgress = false;
    });
  }

  createNote(callback?: any): void {
    this.errorMsg = false;
    this.noteService.createNote(this.newNote)
      .subscribe(result => {
        if (result) {
          this.notes.push(result);
          this.closeForm();
        }
        return callback && callback();
      }, _ => this.errorMsg = true);
  }

  initForm(): void {
    this.isNewNoteFormOpened = true;
  }

  closeForm(): void {
    this.newNote = new Note();
    this.isNewNoteFormOpened = false;
    this.errorMsg = false;
  }

}
