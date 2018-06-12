import { Injectable } from '@angular/core';
import { NoteElement } from './note-element';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class NoteElementService {

  private notesElementsUrl = 'api/notes-elements';

  constructor(private http: HttpClient) { }

  getNoteElements(noteId: number): Observable<NoteElement[]> {
    const url = `${this.notesElementsUrl}/${noteId}`;
    return this.http.get<NoteElement[]>(url);
  }

  swap(noteElementId: number, sortBy: number): Observable<NoteElement[]> {
    const url = `${this.notesElementsUrl}/swap`;
    return this.http.post<NoteElement[]>(url, {noteElementId: noteElementId, sortBy: sortBy});
  }

  createNoteElement(noteElement: NoteElement): Observable<NoteElement> {
    return this.http.post<NoteElement>(this.notesElementsUrl, noteElement);
  }

  deleteNoteElement(id: number): Observable<any> {
    const url = `${this.notesElementsUrl}/${id}`;
    return this.http.delete(url);
  }

  editNoteElement(noteElement: NoteElement): Observable<NoteElement> {
    return this.http.put<NoteElement>(this.notesElementsUrl, noteElement);
  }

}
