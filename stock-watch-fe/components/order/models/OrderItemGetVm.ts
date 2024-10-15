export interface OrderItemGetVm {
  id: number;
  stockId: number;
  stockName: string;
  stockSymbol: string;
  quantity: number;
  stockPrice: number; // BigDecimal represented as number in TypeScript
}
