import {Component, OnInit} from '@angular/core';
import {Order} from "../type";
import {ActivatedRoute, Router} from "@angular/router";
import {OrderService} from "../../../services/order.service";

@Component({
  selector: 'app-cancel',
  templateUrl: './cancel.component.html',
  styleUrls: ['./cancel.component.scss']
})
export class CancelComponent implements OnInit {

  order: Order;

  constructor(private router: Router, private route: ActivatedRoute, private orderService: OrderService) {
  }

  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.orderService.get(id).subscribe(order => {
      this.order = order;
    });
  }

  cancel(form: any) {
    this.orderService.cancel({orderId: this.order.orderId, accessCode: form.accessCode})
        .subscribe(value => {
          this.router.navigate(['/view', this.order.orderId]);
        })
  }


}
