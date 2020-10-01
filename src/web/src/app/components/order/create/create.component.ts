import {Component, OnInit} from '@angular/core';
import {Order} from "../type";
import {OrderService} from "../../../services/order.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.scss']
})
export class CreateComponent implements OnInit {

  constructor(private orderService: OrderService, private router: Router) {
  }

  ngOnInit(): void {
  }

  create(form: Order) {
    this.orderService.create(form).subscribe(order => {
      this.router.navigate(['/view', order.orderId]);
    });
  }
}
