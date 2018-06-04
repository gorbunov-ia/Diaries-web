import { Component, OnInit, Inject, Input, Output, EventEmitter } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Note } from '../note';
import { NoteService } from '../note.service';


@Component({
  selector: 'app-note-editor',
  templateUrl: './note-editor.component.html',
  styleUrls: ['./note-editor.component.css']
})
export class NoteEditorComponent implements OnInit {

  @Input() newNote: Note;
  @Input() isNewNoteFormOpened: boolean;
  errorMsg: boolean;
  onProgress: boolean;
  @Output() result = new EventEmitter<Note>();

  constructor(private noteService: NoteService) { }

  ngOnInit() {
    this.closeForm();
  }

  onSubmit(form: NgForm): void {
    if (!form.valid) {
      return;
    }
    this.onProgress = true;
    if (this.newNote.id) {
      this.editNote(_ => this.afterResponse(form));
    } else {
      this.createNote(_ => this.afterResponse(form));
    }
  }

  createNote(callback?: any): void {
    this.errorMsg = false;
    this.noteService.createNote(this.newNote)
      .subscribe(result => {
        if (result) {
          this.result.emit(result);
          // this.notes.push(result);
          // this.sortNotes();
          this.closeForm();
        }
        return callback && callback();
      }, _ => this.errorMsg = true);
  }

  editNote(callback?: any): void {
    this.errorMsg = false;
    this.noteService.editNote(this.newNote)
      .subscribe(result => {
        if (result) {
          this.result.emit(result);
          /*
          for (let i = 0; i < this.notes.length; i++) {
            if (this.notes[i].id === result.id) {
              this.notes[i] = result;
              break;
            }
          }
          this.sortNotes();
          */
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

  private afterResponse = (form: NgForm): void => {
    form.reset();
    this.onProgress = false;
  }

}
