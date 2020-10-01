import {Component, OnInit} from '@angular/core';
import {OrderService} from "../../../services/order.service";
import {Order} from "../type";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-confirm',
  templateUrl: './confirm.component.html',
  styleUrls: ['./confirm.component.scss']
})
export class ConfirmComponent implements OnInit {
  order: Order;

  constructor(private router: Router, private route: ActivatedRoute, private orderService: OrderService) {
  }

  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.orderService.get(id).subscribe(order => {
      this.order = order;
    });
  }

  confirm(form: any) {
    this.orderService.confirm({orderId: this.order.orderId, accessCode: form.accessCode})
        .subscribe(value => {
          this.router.navigate(['/view', this.order.orderId]);
        })
  }

}
