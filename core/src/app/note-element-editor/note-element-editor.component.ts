import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NoteElement } from '../note-element';
import { NoteElementService } from '../note-element.service';

@Component({
  selector: 'app-note-element-editor',
  templateUrl: './note-element-editor.component.html',
  styleUrls: ['./note-element-editor.component.css']
})
export class NoteElementEditorComponent implements OnInit {

  @Input() element: NoteElement;
  @Input() isFormOpen: boolean;
  @Input() noteId: number;
  errorMsg: boolean;
  onProgress: boolean;
  @Output() result = new EventEmitter<NoteElement>();

  constructor(private noteElementService: NoteElementService) { }

  ngOnInit() {
    this.closeForm();
  }

  onSubmit(form: NgForm): void {
    if (!form.valid) {
      return;
    }
    this.onProgress = true;
    if (this.element.id) {
      this.editNoteElement(_ => this.afterResponse(form));
    } else {
      this.createNoteElement(_ => this.afterResponse(form));
    }
  }

  createNoteElement(callback?: any): void {
    this.errorMsg = false;
    this.noteElementService.createNoteElement(this.element)
      .subscribe(result => {
        if (result) {
          this.result.emit(result);
          this.closeForm();
        }
        return callback && callback();
      }, _ => this.errorMsg = true);
  }

  editNoteElement(callback?: any): void {
    this.errorMsg = false;
    this.noteElementService.editNoteElement(this.element)
      .subscribe(result => {
        if (result) {
          this.result.emit(result);
          this.closeForm();
        }
        return callback && callback();
      }, _ => this.errorMsg = true);
  }

  initForm(): void {
    this.element = new NoteElement();
    this.element.noteId = this.noteId;
    this.isFormOpen = true;
  }

  closeForm(): void {
    this.isFormOpen = false;
    this.errorMsg = false;
  }

  private afterResponse = (form: NgForm): void => {
    form.reset();
    this.onProgress = false;
  }

}
