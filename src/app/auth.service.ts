import { Injectable } from '@angular/core';
import { User } from './user';
import { USERS } from './mock-users';

@Injectable()
export class AuthService {

  private user: User;
  redirectUrl: string;

  constructor() { }

  isAuthenticated(): boolean {
    return this.user != null;
  }

  login(username: string, password: string): User {
    this.user = null;
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
  }

  getUser(): User {
    return this.user;
  }

}
