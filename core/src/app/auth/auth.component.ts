import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { Credentials } from '../credentials';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {

  errorMsg: boolean;

  credentials: Credentials = {username: '', password: ''};

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit() {
  }

  // todo: wait logo
  login(): void {
    this.errorMsg = false;

    this.authService.authenticate(this.credentials, () => {
      if (!this.authService.isAuthenticated()) {
        this.errorMsg = true;
        return;
      }
      const redirect = this.authService.getRedirectUrl() ? this.authService.getRedirectUrl() : '/home';
      this.authService.setRedirectUrl(null);
      this.router.navigate([redirect]);
    });
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
