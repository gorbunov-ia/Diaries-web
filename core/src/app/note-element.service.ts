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
    const url = `${this.notesElementsUrl}/swap?note-element-id=${noteElementId}&sort-by=${sortBy}`;
    return this.http.post<NoteElement[]>(url, null);
  }
}
