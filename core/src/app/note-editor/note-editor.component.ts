import { Component, OnInit, Inject, Input } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Note } from '../note';
import { NoteService } from '../note.service';
import { map, catchError } from 'rxjs/operators';
import { of } from 'rxjs/observable/of';

@Component({
  selector: 'app-note-editor',
  templateUrl: './note-editor.component.html',
  styleUrls: ['./note-editor.component.css']
})
export class NoteEditorComponent implements OnInit {

  private errorMsg;
  private onProgress;
  @Input() note: Note;

  constructor(private noteService: NoteService) { }

  ngOnInit() {

  }

  initHero(): void {
    this.note = new Note();
  }

  clearHero(): void {
    this.note = null;
    this.errorMsg = false;
  }

}
