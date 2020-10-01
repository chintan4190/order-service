import {Component, OnInit} from '@angular/core';
import {OrderService} from "../../services/order.service";
import {Order} from "../order/type";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {

  orders: Order[];

  constructor(private orderService: OrderService) {
  }

  ngOnInit(): void {
    this.orderService.list().subscribe(list => {
      this.orders = list;
    });
  }

}
