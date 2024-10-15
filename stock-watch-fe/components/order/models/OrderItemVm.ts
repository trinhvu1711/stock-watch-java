export interface OrderItemVm {
  id: number;
  stockId: number;
  stockName: string;
  stockSymbol: string;
  quantity: number;
  stockPrice: number; // BigDecimal in Java can be represented as number in TypeScript
  note: string;
  orderId: number;
}
