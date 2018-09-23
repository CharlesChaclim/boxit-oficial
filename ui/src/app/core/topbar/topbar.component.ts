import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-topbar',
  templateUrl: './topbar.component.html',
  styleUrls: ['./topbar.component.scss']
})
export class TopbarComponent implements OnInit {
  navbar = false;

  constructor() { }

  ngOnInit() {
  }

  toggle() {
    this.navbar = !this.navbar;
  }
}
