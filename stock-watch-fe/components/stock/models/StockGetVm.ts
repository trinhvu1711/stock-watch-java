export interface StocksGetVm {
  symbol: string;
  name: string;
  exchange: string;
  currentPrice: number;
  openPrice: number;
  closePrice: number;
  highPrice: number;
  lowPrice: number;
  volume: number;
}
