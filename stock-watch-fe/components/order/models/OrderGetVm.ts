import { OrderItemGetVm } from "./OrderItemGetVm";

export interface OrderGetVm {
  id: number;
  total: number; // BigDecimal represented as number in TypeScript
  orderItems: OrderItemGetVm[];
  createdOn: string; // ZonedDateTime as ISO string (e.g., "2023-10-15T14:48:00Z")
}
