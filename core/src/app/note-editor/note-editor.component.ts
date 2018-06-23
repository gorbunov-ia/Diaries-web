import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
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
  errorMsg: boolean;
  onProgress: boolean;
  @Output() result = new EventEmitter<Note>();
  @Output() exit = new EventEmitter<void>();

  constructor(private noteService: NoteService) { }

  ngOnInit() {
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
          this.closeForm();
        }
        return callback && callback();
      }, _ => this.errorMsg = true);
  }

  closeForm(): void {
    this.errorMsg = false;
    this.exit.emit();
  }

  private afterResponse = (form: NgForm): void => {
    form.reset();
    this.onProgress = false;
  }

}
