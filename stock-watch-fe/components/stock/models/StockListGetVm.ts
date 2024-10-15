import { StocksGetVm } from "./StockGetVm";

export interface StockListGetVm {
  stocks: StocksGetVm[];
  pageNo: number;
  pageSize: number;
  totalElement: number;
  totalPage: number;
  isLast: boolean;
}
