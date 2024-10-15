import {
  StockPostVm,
  StockPutVm,
  StockPurchaseVm,
  StocksGetVm,
  StockListGetVm,
} from "@/components/stock/models";
import { StockGetDetailVm } from "../models/StockGetDetailVm";

const baseUrl = "http://localhost:8222/api/v1/stocks";

// Add Stock
export async function addStock(stockPostVm: StockPostVm): Promise<StocksGetVm> {
  const response = await fetch(baseUrl, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(stockPostVm),
  });
  return response.json();
}

// List Stocks
export async function listStocks(
  pageNo = 0,
  pageSize = 5,
  stockSymbol = "",
  stockName = ""
): Promise<StockListGetVm> {
  const response = await fetch(
    baseUrl +
      `?pageNo=${pageNo}&pageSize=${pageSize}&stockSymbol=${stockSymbol}&stockName=${stockName}`
  );
  return response.json();
}

// Get Stock By ID
export async function getStockById(id: number): Promise<StocksGetVm> {
  const response = await fetch(
    process.env.API_BASE_PATH + `/api/v1/stocks/${id}`
  );
  return response.json();
}

// Get Stock By ID
export async function getStockBySymbol(
  symbol: string
): Promise<StockGetDetailVm> {
  const response = await fetch(baseUrl + `/symbol?stockSymbol=${symbol}`);
  return response.json();
}

// Update Stock
export async function updateStock(
  id: number,
  updatedStock: StockPutVm
): Promise<void> {
  await fetch(process.env.API_BASE_PATH + `/api/v1/stocks/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(updatedStock),
  });
}

// Delete Stock
export async function deleteStock(id: number): Promise<void> {
  await fetch(process.env.API_BASE_PATH + `/api/v1/stocks/${id}`, {
    method: "DELETE",
  });
}

// Subtract Stock Quantity
export async function subtractStockQuantity(
  stockPurchaseVms: StockPurchaseVm[]
): Promise<void> {
  await fetch(process.env.API_BASE_PATH + `/api/v1/stocks/subtract-quantity`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(stockPurchaseVms),
  });
}
