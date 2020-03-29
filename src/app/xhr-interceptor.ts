import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';
import { AuthService } from './auth.service';

@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler) {
    const userToken = this.authService.getUserToken();
    if (userToken) {
      request = request.clone({setHeaders: {'X-Auth-Token': userToken}});
    }
    return next.handle(request);
  }
}
