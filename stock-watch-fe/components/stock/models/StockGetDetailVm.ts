import { StocksPriceGetVm } from "./StockPriceVm";

export interface StockGetDetailVm {
  symbol: string;
  name: string;
  exchange: string;
  currentPrice: number;
  openPrice: number;
  closePrice: number;
  highPrice: number;
  lowPrice: number;
  volume: number;
  availableQuantity: number;
  isPublished: boolean;
  isFeatured: boolean;
  isVisibleIndividually: boolean;
  stockTrackingEnabled: boolean;
  stockPrices: Set<StocksPriceGetVm>;
}
