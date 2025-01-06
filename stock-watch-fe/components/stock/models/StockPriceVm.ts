export interface StocksPriceGetVm {
  openPrice: number; // Represents the opening price of the stock
  closePrice: number; // Represents the closing price of the stock
  highPrice: number; // Represents the highest price of the stock during the period
  lowPrice: number; // Represents the lowest price of the stock during the period
  volume: number; // Represents the volume of stocks traded
  timestamp: string; // Represents the timestamp of the stock prices in ISO format (e.g., "2024-10-12T09:45:50.017184800")
}
