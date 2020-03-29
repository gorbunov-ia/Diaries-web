import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { Credentials } from '../credentials';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {

  errorMsg: boolean;
  onProgress: boolean;

  credentials: Credentials = {username: '', password: ''};

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit() {
  }

  onSubmit(form: NgForm): void {
    if (!form.valid) {
      return;
    }
    this.onProgress = true;
    this.login(_ => {
      form.reset();
      this.onProgress = false;
    });
  }

  private login(callback?: any): void {
    this.errorMsg = false;

    this.authService.authenticate(this.credentials)
      .subscribe(result => {
        if (!result) {
          this.errorMsg = true;
          return callback && callback();
        }
        const redirect = this.authService.getRedirectUrl() ? this.authService.getRedirectUrl() : '/home';
        this.authService.setRedirectUrl(null);
        this.router.navigate([redirect]);
        return callback && callback();
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
