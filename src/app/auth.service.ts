import { Injectable } from '@angular/core';
import { User } from './user';
import { USERS } from './mock-users';

@Injectable()
export class AuthService {

  private user: User;
  private logoutMsg: boolean;
  private redirectUrl: string;

  constructor() { }

  isAuthenticated(): boolean {
    return this.user != null;
  }

  isLogoutMsg(): boolean {
    return this.logoutMsg;
  }

  login(username: string, password: string): User {
    this.user = null;
    this.logoutMsg = false;
    for (const user of USERS) {
      if (user.username === username) {
        this.user = user;
        return this.user;
      }
    }
    return this.user;
  }

  logout(): void {
    this.user = null;
    this.logoutMsg = true;
  }

  getUser(): User {
    return this.user;
  }

  getRedirectUrl(): string {
    return this.redirectUrl;
  }

  setRedirectUrl(redirectUrl: string) {
    this.redirectUrl = redirectUrl;
  }

}
