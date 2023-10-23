import { Component, OnInit } from '@angular/core';
import { RestService } from './rest.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'feapp';
  public data:any;

  constructor(private restService: RestService) {

  }

  ngOnInit(): void {
    this.loadData();
  }

  public loadData() {
    this.restService.get('http://localhost:8080/student/1')
    .subscribe(response => {
      console.log(response);
      this.data = response;
    })
  }
}
