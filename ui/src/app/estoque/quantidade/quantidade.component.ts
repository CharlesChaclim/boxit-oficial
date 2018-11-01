import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-core',
  templateUrl: './quantidade.component.html',
  styleUrls: ['./quantidade.component.scss']
})
export class QuantidadeComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  back() {
    history.back();
  }
}
