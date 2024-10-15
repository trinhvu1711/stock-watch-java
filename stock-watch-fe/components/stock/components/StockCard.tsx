import Link from "next/link";
import React from "react";
import {
  ArrowDownIcon,
  ArrowUpIcon,
  CarrotIcon,
  PackagePlusIcon,
  StarIcon,
} from "lucide-react";
import { Button } from "@/components/ui/button";
import { StockPurchaseVm } from "../models";

const handleAddToCart = (stock: StockPurchaseVm) => {
  // addToCart(stock); // Call the addToCart function from the CartService
};

export default function StockCard({ stock, onToggleWatchlist, isWatchlisted }) {
  return (
    <div className="bg-white overflow-hidden shadow rounded-lg">
      <div className="px-4 py-5 sm:p-6">
        <div className="flex items-center justify-between">
          <div>
            <Link
              href={`/stocks/${stock.symbol}`}
              className="text-lg leading-6 font-medium text-gray-900 hover:underline"
            >
              {stock.symbol}
            </Link>
            <p className="text-sm text-gray-500">{stock.name}</p>
          </div>
          <div className="text-right">
            <p className="text-2xl font-semibold text-gray-900">
              ${stock.currentPrice.toFixed(2)}
            </p>
            <p
              className={`flex items-center text-sm ${
                stock.currentPrice - stock.closePrice >= 0
                  ? "text-green-600"
                  : "text-red-600"
              }`}
            >
              {stock.currentPrice - stock.closePrice >= 0 ? (
                <ArrowUpIcon className="h-4 w-4 mr-1" />
              ) : (
                <ArrowDownIcon className="h-4 w-4 mr-1" />
              )}
              {Math.abs(stock.currentPrice - stock.closePrice).toFixed(2)}
            </p>
          </div>
        </div>
        <div className="flex flex-row justify-between">
          <Button
            variant="ghost"
            size="sm"
            className="mt-4"
            onClick={() => handleAddToCart(stock)}
          >
            <PackagePlusIcon className={`h-5 w-5 `} />
          </Button>
          <Button
            variant="ghost"
            size="sm"
            className="mt-4"
            onClick={() => onToggleWatchlist(stock.symbol)}
          >
            <StarIcon
              className={`h-5 w-5 ${
                isWatchlisted
                  ? "text-yellow-400 fill-yellow-400"
                  : "text-gray-400"
              }`}
            />
            <span className="sr-only">
              {isWatchlisted ? "Remove from watchlist" : "Add to watchlist"}
            </span>
          </Button>
        </div>
      </div>
    </div>
  );
}
