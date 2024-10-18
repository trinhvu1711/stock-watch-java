export interface PaymentGetVm {
  orderId: number;
  checkOutId: string;
  amount: number;
  paymentMethod: string;
  paymentStatus: string;
  createdOn: string;
}
