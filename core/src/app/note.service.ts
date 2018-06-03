import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Note } from './note';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class NoteService {

  private notesUrl = 'api/notes';

  constructor(private http: HttpClient) { }

  getNotes(): Observable<Note[]> {
    return this.http.get<Note[]>(this.notesUrl);
  }

  getNote(id: number): Observable<Note> {
    const url = `${this.notesUrl}/${id}`;
    return this.http.get<Note>(url);
  }

  createNote(note: Note): Observable<Note> {
    return this.http.post<Note>(this.notesUrl, note);
  }

  deleteNote(id: number): Observable<any> {
    const url = `${this.notesUrl}/${id}`;
    return this.http.delete(url);
  }

  editNote(note: Note): Observable<Note> {
    return this.http.put<Note>(this.notesUrl, note);
  }

}
