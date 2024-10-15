import { EOrderStatus } from "./EOrderStatus";
import { OrderItemVm } from "./OrderItemVm";

export interface OrderVm {
  id: number;
  email: string;
  note: string;
  numberItem: number;
  totalPrice: number; // BigDecimal represented as number in TypeScript
  orderStatus: EOrderStatus;
  paymentStatus: string;
  paymentId: number;
  orderItemVms: Set<OrderItemVm>; // Set to manage the unique list of items

  checkoutId: string;
}
