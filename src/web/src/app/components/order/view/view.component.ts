import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Order} from "../type";
import {OrderService} from "../../../services/order.service";

@Component({
  selector: 'app-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.scss']
})
export class ViewComponent implements OnInit {
  order: Order;

  constructor(private router: Router, private route: ActivatedRoute, private orderService: OrderService) {
  }

  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.orderService.get(id).subscribe(order => {
      this.order = order;
    });
  }

}
