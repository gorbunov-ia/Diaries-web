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
  errorMsg: boolean;
  onProgress: boolean;
  @Output() result = new EventEmitter<NoteElement>();
  @Output() exit = new EventEmitter<void>();

  constructor(private noteElementService: NoteElementService) { }

  ngOnInit() {
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
          this.closeNoteElementForm();
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
          this.closeNoteElementForm();
        }
        return callback && callback();
      }, _ => this.errorMsg = true);
  }

  deleteNoteElement(callback?: any): void {
    this.errorMsg = false;
    this.noteElementService.deleteNoteElement(this.element.id)
      .subscribe(_ => {
        this.result.emit(null);
        this.closeNoteElementForm();
        return callback && callback();
      }, _ => this.errorMsg = true);
}

  closeNoteElementForm(): void {
    this.errorMsg = false;
    this.exit.emit();
  }

  private afterResponse = (form: NgForm): void => {
    form.reset();
    this.onProgress = false;
  }

}
