"use client";
import { useEffect, useState } from "react";
import StockCard from "@/components/stock/components/StockCard";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { listStocks } from "../services/StockService";
import { StocksGetVm } from "../models";

export default function StockList() {
  const [stocks, setStocks] = useState<StocksGetVm[]>([]);
  const [watchlist, setWatchlist] = useState<string[]>([]);
  const [activeTab, setActiveTab] = useState("All Stocks");

  const toggleWatchlist = (symbol: string) => {
    setWatchlist((prev) =>
      prev.includes(symbol)
        ? prev.filter((item) => item !== symbol)
        : [...prev, symbol]
    );
  };

  // Fetch data when the component mounts
  useEffect(() => {
    listStocks().then((res) => {
      setStocks(res.stocks);
    });
  }, []);

  return (
    <main className="flex-grow">
      <div className="mx-auto">
        <h2 className="text-2xl font-semibold text-gray-900 mb-6">
          Market Overview
        </h2>

        {/* Tabs for Stock Segments */}
        <Tabs value={activeTab} onValueChange={(value) => setActiveTab(value)}>
          <TabsList>
            <TabsTrigger value="All Stocks">All Stocks</TabsTrigger>
            <TabsTrigger value="Watchlist">Watchlist</TabsTrigger>
          </TabsList>

          <TabsContent value="All Stocks">
            <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
              {stocks.map((stock) => (
                <StockCard
                  key={stock.symbol}
                  stock={stock}
                  onToggleWatchlist={toggleWatchlist}
                  isWatchlisted={watchlist.includes(stock.symbol)}
                />
              ))}
            </div>
          </TabsContent>

          <TabsContent value="Watchlist">
            <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
              {stocks
                .filter((stock) => watchlist.includes(stock.symbol))
                .map((stock) => (
                  <StockCard
                    key={stock.symbol}
                    stock={stock}
                    onToggleWatchlist={toggleWatchlist}
                    isWatchlisted={watchlist.includes(stock.symbol)}
                  />
                ))}
            </div>
          </TabsContent>
        </Tabs>
      </div>
    </main>
  );
}
