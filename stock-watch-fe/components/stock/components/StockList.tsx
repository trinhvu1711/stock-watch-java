"use client";
import StockCard from "@/components/stock/components/StockCard";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { useState } from "react";

const initialStocks = [
  { symbol: "AAPL", name: "Apple Inc.", price: 150.25, change: 2.5 },
  { symbol: "GOOGL", name: "Alphabet Inc.", price: 2750.8, change: -15.2 },
  {
    symbol: "MSFT",
    name: "Microsoft Corporation",
    price: 305.15,
    change: 0.75,
  },
  { symbol: "AMZN", name: "Amazon.com, Inc.", price: 3380.5, change: -22.3 },
  { symbol: "FB", name: "Meta Platforms, Inc.", price: 325.45, change: 5.2 },
  { symbol: "TSLA", name: "Tesla, Inc.", price: 850.75, change: 10.5 },
];

export default function StockList() {
  const [stocks, setStocks] = useState(initialStocks);
  const [watchlist, setWatchlist] = useState<string[]>([]);

  const toggleWatchlist = (symbol: string) => {
    if (watchlist.includes(symbol)) {
      setWatchlist(watchlist.filter((item) => item !== symbol));
    } else {
      setWatchlist([...watchlist, symbol]);
    }
  };

  return (
    <main className="flex-grow">
      <div className="mx-auto">
        <h2 className="text-2xl font-semibold text-gray-900 mb-6">
          Market Overview
        </h2>
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
      </div>
    </main>
  );
}
