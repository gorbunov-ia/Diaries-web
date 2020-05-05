import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Credentials } from './credentials';
import { Observable, of } from 'rxjs';
import { map, catchError, finalize } from 'rxjs/operators';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = environment.baseUrl;
  private authUrl = this.baseUrl + 'api/user/login';
  private logoutUrl = this.baseUrl + 'api/user/logout';

  private userToken: string;
  private authenticated: boolean;
  private logoutMsg: boolean;
  private redirectUrl: string;

  constructor(private http: HttpClient) {
    this.refreshUserToken();
  }

  isAuthenticated(): boolean {
    return this.authenticated;
  }

  isLogoutMsg(): boolean {
    return this.logoutMsg;
  }

  authenticate(credentials: Credentials): Observable<boolean> {
    this.logoutMsg = false;

    const headers = new HttpHeaders(credentials ? {
        authorization : 'Basic ' + btoa(credentials.username + ':' + credentials.password)
    } : {});

    return this.http.post<AuthResponce>(this.authUrl, null, {headers, observe: 'response'})
      .pipe(
        map(response => {
          const token = response.headers.get('X-Auth-Token');
          if (response.body.id && token) {
            localStorage.setItem('userToken', token);
          }
          this.refreshUserToken();
          return this.authenticated;
        }),
        catchError(_ => of(false)));
  }

  logout(): void {
    this.http.post(this.logoutUrl, {}).pipe(finalize(() => {
        localStorage.removeItem('userToken');
        this.refreshUserToken();
        this.logoutMsg = true;
    })).subscribe();
  }

  getUserToken(): string {
    return this.userToken;
  }

  getRedirectUrl(): string {
    return this.redirectUrl;
  }

  setRedirectUrl(redirectUrl: string) {
    this.redirectUrl = redirectUrl;
  }

  private refreshUserToken(): void {
    this.userToken = localStorage.getItem('userToken');
    this.authenticated = !!this.userToken;
  }

}
export interface AuthResponce {
  id: string;
}
