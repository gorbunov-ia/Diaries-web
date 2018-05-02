import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  greeting: any = {'id': 'XXX', 'content': 'Hello World'};

  // constructor() { }
  constructor(private http: HttpClient) {
    this.greeting = {'id': 'ZZZ', 'content': 'Hello World'};
    http.get('resource').subscribe(data => {
      console.log(data);
      this.greeting = data;
      });
  }

  ngOnInit() {
  }

}
