export interface CheckoutItemVm {
  id: number;
  stockId: number;
  stockName: string;
  quantity: number;
  price: number; // BigDecimal represented as number in TypeScript
  note: string;
  checkoutId: string;
}
