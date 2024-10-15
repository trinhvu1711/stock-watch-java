export interface CheckoutItemPostVm {
  stockId: number;
  stockName: string;
  quantity: number;
  price: number; // BigDecimal represented as number in TypeScript
  note?: string; // Optional property, add '?' if it can be null or undefined
}
