import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { User } from '../user';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {

  errorMsg: boolean;

  username: string;
  password: string;

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit() {
  }

  login(): void {
    this.errorMsg = false;

    const user = this.authService.login(this.username, this.password);
    if (!user) {
      this.errorMsg = true;
      return;
    }
    const redirect = this.authService.getRedirectUrl() ? this.authService.getRedirectUrl() : '/home';
    this.router.navigate([redirect]);
  }

  logout(): void {
    this.authService.logout();
  }

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

  isLogoutMsg(): boolean {
    return this.authService.isLogoutMsg();
  }

}
