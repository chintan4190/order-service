export interface Order {
  orderId?: number;
  cardNumber: string;
  passNumber: string;
  amount: string;
  orderStatus?: string;
}

export interface OrderAccess {
  orderId: number;
  accessCode: any;
}
