import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NoteElement } from './note-element';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NoteElementService {

  private baseUrl = 'http://diaries-server-diaries.apps.ca-central-1.starter.openshift-online.com/';
  private notesElementsUrl = this.baseUrl + 'api/notes-elements';

  constructor(private http: HttpClient) { }

  getNoteElements(noteId: number): Observable<NoteElement[]> {
    const url = `${this.notesElementsUrl}/${noteId}`;
    return this.http.get<NoteElement[]>(url);
  }

  swap(noteElementId: number, sortBy: number): Observable<NoteElement[]> {
    const url = `${this.notesElementsUrl}/swap`;
    return this.http.post<NoteElement[]>(url, {noteElementId, sortBy});
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
