import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title: String = 'app work';
  year: number = (new Date).getFullYear();
  module: String = 'home';
  isAuthenticated: Boolean = true;
}
