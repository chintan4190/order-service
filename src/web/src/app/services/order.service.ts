import {Injectable} from '@angular/core';
import {Order, OrderAccess} from "../components/order/type";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'X-Requested-With': 'XMLHttpRequest'
    })
  };

  constructor(private http: HttpClient) {
  }

  list(): Observable<Order[]> {
    return this.http.get<Order[]>('/order/list', this.httpOptions);

  }

  get(id: number): Observable<Order> {
    return this.http.get<Order>('/order/' + id, this.httpOptions);
  }

  create(order: Order): Observable<any> {
    console.log('Create:', order);
    return this.http.post<any>('/order/create', order, this.httpOptions);

  }

  confirm(order: OrderAccess): Observable<any> {
    console.log('Confirm:', order);
    return this.http.post<any>('/order/confirm', order, this.httpOptions);
  }

  cancel(order: OrderAccess): Observable<any> {
    console.log('Cancel:', order);
    return this.http.post<any>('/order/cancel/', order, this.httpOptions);
  }

}
