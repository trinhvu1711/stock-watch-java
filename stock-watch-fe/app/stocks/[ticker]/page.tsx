"use client";

import {
  ArrowDownIcon,
  ArrowUpIcon,
  StarIcon,
  TrendingUpIcon,
  TrendingDownIcon,
  BarChart3Icon,
  DollarSignIcon,
} from "lucide-react";
import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Label } from "@/components/ui/label";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  ChartContainer,
  ChartTooltip,
  ChartTooltipContent,
} from "@/components/ui/chart";
import {
  Line,
  LineChart,
  ResponsiveContainer,
  XAxis,
  YAxis,
  Tooltip,
  Legend,
  Bar,
  BarChart,
} from "recharts";
import { Progress } from "@/components/ui/progress";
import { Badge } from "@/components/ui/badge";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { getStockBySymbol } from "@/components/stock/services/StockService";
import { StockGetDetailVm } from "@/components/stock/models/StockGetDetailVm";
import { CheckoutItemPostVm } from "@/components/order/models/CheckoutItemPostVm";
import { CheckoutPostVm } from "@/components/order/models/CheckoutPostVm";
import { createCheckout } from "@/components/order/services/OrderService";
import { useRouter } from "next/navigation";
type Props = {
  params: {
    ticker: string;
  };
  searchParams?: {
    ticker?: string;
    range?: string;
    interval?: string;
  };
};

// Mock function to get stock details (expanded)
const getStockDetails = (symbol) => {
  return {
    symbol: symbol,
    name: "Example Company Inc.",
    price: 150.25,
    change: 2.5,
    marketCap: "2.5T",
    peRatio: 30.5,
    dividend: 1.5,
    volume: "10.2M",
    avgVolume: "8.5M",
    high52: 180.75,
    low52: 120.3,
    open: 148.5,
    previousClose: 147.75,
    eps: 5.25,
    beta: 1.2,
    description:
      "Example Company Inc. is a leading technology firm specializing in innovative software solutions and cutting-edge hardware. With a global presence and a strong focus on research and development, the company has consistently delivered groundbreaking products that have transformed various industries. Their commitment to sustainability and ethical practices has earned them a reputation as a responsible corporate citizen.",
    historicalData: [
      { date: "2023-01-01", price: 120 },
      { date: "2023-02-01", price: 125 },
      { date: "2023-03-01", price: 130 },
      { date: "2023-04-01", price: 128 },
      { date: "2023-05-01", price: 135 },
      { date: "2023-06-01", price: 140 },
      { date: "2023-07-01", price: 138 },
      { date: "2023-08-01", price: 145 },
      { date: "2023-09-01", price: 150 },
      { date: "2023-10-01", price: 148 },
      { date: "2023-11-01", price: 155 },
      { date: "2023-12-01", price: 150.25 },
    ],
    news: [
      {
        title: "Example Co. Announces New Product Line",
        date: "2023-12-01",
        sentiment: "positive",
      },
      {
        title: "Q3 Earnings Beat Expectations",
        date: "2023-11-15",
        sentiment: "positive",
      },
      {
        title: "Industry Challenges May Impact Growth",
        date: "2023-11-05",
        sentiment: "negative",
      },
    ],
    analystRecommendations: [
      { firm: "Alpha Investments", recommendation: "Buy", targetPrice: 180 },
      { firm: "Beta Securities", recommendation: "Hold", targetPrice: 155 },
      {
        firm: "Gamma Research",
        recommendation: "Strong Buy",
        targetPrice: 200,
      },
    ],
    keyRatios: {
      priceToBook: 5.2,
      debtToEquity: 0.4,
      quickRatio: 1.5,
      currentRatio: 2.1,
      returnOnEquity: 22.5,
      profitMargin: 15.3,
    },
    sectorPerformance: [
      { name: "Example Co.", performance: 12.5 },
      { name: "Competitor A", performance: 10.2 },
      { name: "Competitor B", performance: -5.3 },
      { name: "Competitor C", performance: 8.7 },
    ],
  };
};

export default function StockDetailPage({ params, searchParams }: Props) {
  const router = useRouter();
  const ticker = params.ticker;
  const [isWatchlisted, setIsWatchlisted] = useState(false);
  const [quantity, setQuantity] = useState(1);
  const [timeframe, setTimeframe] = useState("1Y");

  const [stock, setStock] = useState<StockGetDetailVm>();
  const stockChart = getStockDetails(ticker);
  useEffect(() => {
    getStockBySymbol(ticker)
      .then((data) => {
        setStock(data);
      })
      .catch((e) => {
        // handle the error as needed
        console.error("An error occurred while fetching the data: ", e);
      });
  }, []);

  const toggleWatchlist = () => {
    setIsWatchlisted(!isWatchlisted);
    // In a real app, you would update the watchlist in the backend here
  };

  const convertItemToCheckoutItem = (
    item: StockGetDetailVm,
    quantity: number
  ): CheckoutItemPostVm => {
    return {
      stockId: item.id,
      stockName: item.name,
      stockSymbol: item.symbol,
      quantity: quantity,
      price: item.currentPrice,
      note: "",
    };
  };

  const convertItemsToCheckoutItems = (
    items: StockGetDetailVm[]
  ): CheckoutItemPostVm[] => {
    return items.map(convertItemToCheckoutItem);
  };

  const handleTrade = (event, action) => {
    event.preventDefault();
    // In a real app, you would send a buy/sell order to the backend here
    console.log(`${action} ${quantity} shares of ${stock!.symbol}`);
    const item = convertItemToCheckoutItem(stock!, quantity);
    console.log("🚀 ~ handleTrade ~ item:", item);
    createCheckout({
      email: "",
      note: "",
      checkoutItemPostVms: [item],
    })
      .then((checkout) => {
        console.log("Checkout created:", checkout);
        router.push(`/checkout/${checkout!.id}`);
      })
      .catch((error) => {
        console.error("An error occurred while creating the checkout:", error);
      });
  };

  return (
    <p>
      {stock ? (
        <div className="w-full mx-auto py-8 flex flex-col sm:py-4 sm:pl-14">
          <div className="gap-4 p-4 sm:px-6 sm:py-0 md:gap-8">
            <div className="mb-8 flex flex-wrap items-center justify-between">
              <div>
                <h1 className="text-3xl font-bold text-gray-900">
                  {stock.name} ({stock.symbol})
                </h1>
                <div className="mt-2 flex items-center">
                  <span className="text-4xl font-bold text-gray-900">
                    ${stock.currentPrice.toFixed(2)}
                  </span>
                  <span
                    className={`ml-2 flex items-center text-sm ${
                      stock.currentPrice - stock.closePrice >= 0
                        ? "text-green-600"
                        : "text-red-600"
                    }`}
                  >
                    {stock.currentPrice - stock.closePrice >= 0 ? (
                      <ArrowUpIcon className="h-5 w-5 mr-1" />
                    ) : (
                      <ArrowDownIcon className="h-5 w-5 mr-1" />
                    )}
                    {Math.abs(stock.currentPrice - stock.closePrice).toFixed(2)}{" "}
                    (
                    {(
                      (stock.currentPrice -
                        stock.closePrice / stock.currentPrice) *
                      100
                    ).toFixed(2)}
                    %)
                  </span>
                </div>
              </div>
              <div className="mt-4 sm:mt-0 flex flex-row justify-center">
                <Button
                  variant="outline"
                  className="mr-2"
                  onClick={toggleWatchlist}
                >
                  <StarIcon
                    className={`h-5 w-5 mr-2 ${
                      isWatchlisted
                        ? "text-yellow-400 fill-yellow-400"
                        : "text-gray-400"
                    }`}
                  />
                  {isWatchlisted ? "Remove from Watchlist" : "Add to Watchlist"}
                </Button>
                <Dialog>
                  <DialogTrigger asChild>
                    <Button>Trade</Button>
                  </DialogTrigger>
                  <DialogContent className="sm:max-w-[425px]">
                    <DialogHeader>
                      <DialogTitle>Trade {stock.symbol} Stock</DialogTitle>
                      <DialogDescription>
                        Enter the number of shares you want to trade.
                      </DialogDescription>
                    </DialogHeader>
                    <Tabs defaultValue="buy">
                      <TabsList className="grid w-full grid-cols-2">
                        <TabsTrigger value="buy">Buy</TabsTrigger>
                        <TabsTrigger value="sell">Sell</TabsTrigger>
                      </TabsList>
                      <TabsContent value="buy">
                        <form onSubmit={(e) => handleTrade(e, "Buy")}>
                          <div className="grid gap-4 py-4">
                            <div className="grid grid-cols-4 items-center gap-4">
                              <Label
                                htmlFor="buy-quantity"
                                className="text-right"
                              >
                                Quantity
                              </Label>
                              <Input
                                id="buy-quantity"
                                type="number"
                                className="col-span-3"
                                value={quantity}
                                onChange={(e) =>
                                  setQuantity(parseInt(e.target.value))
                                }
                                min="1"
                              />
                            </div>
                            <div className="grid grid-cols-4 items-center gap-4">
                              <Label className="text-right">Total</Label>
                              <div className="col-span-3">
                                ${(quantity * stock.currentPrice).toFixed(2)}
                              </div>
                            </div>
                          </div>
                          <DialogFooter>
                            <Button type="submit">Confirm Purchase</Button>
                          </DialogFooter>
                        </form>
                      </TabsContent>
                      <TabsContent value="sell">
                        <form onSubmit={(e) => handleTrade(e, "Sell")}>
                          <div className="grid gap-4 py-4">
                            <div className="grid grid-cols-4 items-center gap-4">
                              <Label
                                htmlFor="sell-quantity"
                                className="text-right"
                              >
                                Quantity
                              </Label>
                              <Input
                                id="sell-quantity"
                                type="number"
                                className="col-span-3"
                                value={quantity}
                                onChange={(e) =>
                                  setQuantity(parseInt(e.target.value))
                                }
                                min="1"
                              />
                            </div>
                            <div className="grid grid-cols-4 items-center gap-4">
                              <Label className="text-right">Total</Label>
                              <div className="col-span-3">
                                ${(quantity * stock.currentPrice).toFixed(2)}
                              </div>
                            </div>
                          </div>
                          <DialogFooter>
                            <Button type="submit">Confirm Sale</Button>
                          </DialogFooter>
                        </form>
                      </TabsContent>
                    </Tabs>
                  </DialogContent>
                </Dialog>
              </div>
            </div>

            <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
              <div className="lg:col-span-2">
                <Card>
                  <CardHeader>
                    <CardTitle>Price Chart</CardTitle>
                  </CardHeader>
                  <CardContent>
                    <Tabs
                      value={timeframe}
                      onValueChange={setTimeframe}
                      className="mb-4"
                    >
                      <TabsList>
                        <TabsTrigger value="1D">1D</TabsTrigger>
                        <TabsTrigger value="1W">1W</TabsTrigger>
                        <TabsTrigger value="1M">1M</TabsTrigger>
                        <TabsTrigger value="3M">3M</TabsTrigger>
                        <TabsTrigger value="1Y">1Y</TabsTrigger>
                        <TabsTrigger value="5Y">5Y</TabsTrigger>
                      </TabsList>
                    </Tabs>
                    <ChartContainer
                      config={{
                        price: {
                          label: "Price",
                          color: "hsl(var(--chart-1))",
                        },
                      }}
                      className="h-[300px]"
                    >
                      <ResponsiveContainer width="100%" height="100%">
                        <LineChart data={stockChart.historicalData}>
                          <XAxis
                            dataKey="date"
                            tickFormatter={(value) =>
                              new Date(value).toLocaleDateString()
                            }
                            fontSize={12}
                          />
                          <YAxis fontSize={12} />
                          <ChartTooltip content={<ChartTooltipContent />} />
                          <Line
                            type="monotone"
                            dataKey="price"
                            strokeWidth={2}
                            activeDot={{ r: 8 }}
                          />
                        </LineChart>
                      </ResponsiveContainer>
                    </ChartContainer>
                  </CardContent>
                </Card>

                <Card className="mt-8">
                  <CardHeader>
                    <CardTitle>Company Overview</CardTitle>
                  </CardHeader>
                  <CardContent>
                    <p className="text-gray-700 mb-4">
                      {stockChart.description}
                    </p>
                    <div className="grid grid-cols-2 gap-4">
                      <div>
                        <h3 className="font-semibold mb-2">Key Statistics</h3>
                        <ul className="space-y-1">
                          <li>Market Cap: {stockChart.marketCap}</li>
                          <li>P/E Ratio: {stockChart.peRatio}</li>
                          <li>Dividend Yield: {stockChart.dividend}%</li>
                          <li>52 Week High: ${stockChart.high52}</li>
                          <li>52 Week Low: ${stockChart.low52}</li>
                        </ul>
                      </div>
                      <div>
                        <h3 className="font-semibold mb-2">
                          Trading Information
                        </h3>
                        <ul className="space-y-1">
                          <li>Volume: {stockChart.volume}</li>
                          <li>Avg. Volume: {stockChart.avgVolume}</li>
                          <li>Open: ${stockChart.open}</li>
                          <li>Previous Close: ${stockChart.previousClose}</li>
                          <li>Beta: {stockChart.beta}</li>
                        </ul>
                      </div>
                    </div>
                  </CardContent>
                </Card>

                <Card className="mt-8">
                  <CardHeader>
                    <CardTitle>Financial Summary</CardTitle>
                  </CardHeader>
                  <CardContent>
                    <div className="grid grid-cols-2 gap-4">
                      <div>
                        <h3 className="font-semibold mb-2">Profitability</h3>
                        <div className="space-y-2">
                          <div>
                            <div className="flex justify-between mb-1">
                              <span>Profit Margin</span>
                              <span>{stockChart.keyRatios.profitMargin}%</span>
                            </div>
                            <Progress
                              value={stockChart.keyRatios.profitMargin}
                              className="h-2"
                            />
                          </div>
                          <div>
                            <div className="flex justify-between mb-1">
                              <span>Return on Equity</span>
                              <span>
                                {stockChart.keyRatios.returnOnEquity}%
                              </span>
                            </div>
                            <Progress
                              value={stockChart.keyRatios.returnOnEquity}
                              className="h-2"
                            />
                          </div>
                        </div>
                      </div>
                      <div>
                        <h3 className="font-semibold mb-2">Financial Health</h3>
                        <div className="space-y-2">
                          <div>
                            <div className="flex justify-between  mb-1">
                              <span>Debt to Equity</span>
                              <span>{stockChart.keyRatios.debtToEquity}</span>
                            </div>
                            <Progress
                              value={stockChart.keyRatios.debtToEquity * 100}
                              className="h-2"
                            />
                          </div>
                          <div>
                            <div className="flex justify-between mb-1">
                              <span>Current Ratio</span>
                              <span>{stockChart.keyRatios.currentRatio}</span>
                            </div>
                            <Progress
                              value={stockChart.keyRatios.currentRatio * 50}
                              className="h-2"
                            />
                          </div>
                        </div>
                      </div>
                    </div>
                  </CardContent>
                </Card>
              </div>

              <div>
                <Card>
                  <CardHeader>
                    <CardTitle>Latest News</CardTitle>
                  </CardHeader>
                  <CardContent>
                    <ul className="space-y-4">
                      {stockChart.news.map((item, index) => (
                        <li
                          key={index}
                          className="border-b pb-2 last:border-b-0"
                        >
                          <h3 className="font-semibold">{item.title}</h3>
                          <div className="flex justify-between items-center mt-1">
                            <span className="text-sm text-gray-500">
                              {item.date}
                            </span>
                            <Badge
                              variant={
                                item.sentiment === "positive"
                                  ? "default"
                                  : "destructive"
                              }
                            >
                              {item.sentiment}
                            </Badge>
                          </div>
                        </li>
                      ))}
                    </ul>
                  </CardContent>
                </Card>

                <Card className="mt-8">
                  <CardHeader>
                    <CardTitle>Analyst Recommendations</CardTitle>
                  </CardHeader>
                  <CardContent>
                    <Table>
                      <TableHeader>
                        <TableRow>
                          <TableHead>Firm</TableHead>
                          <TableHead>Recommendation</TableHead>
                          <TableHead>Target</TableHead>
                        </TableRow>
                      </TableHeader>
                      <TableBody>
                        {stockChart.analystRecommendations.map((rec, index) => (
                          <TableRow key={index}>
                            <TableCell>{rec.firm}</TableCell>
                            <TableCell>{rec.recommendation}</TableCell>
                            <TableCell>${rec.targetPrice}</TableCell>
                          </TableRow>
                        ))}
                      </TableBody>
                    </Table>
                  </CardContent>
                </Card>

                <Card className="mt-8">
                  <CardHeader>
                    <CardTitle>Sector Performance</CardTitle>
                  </CardHeader>
                  <CardContent>
                    <ResponsiveContainer width="100%" height={200}>
                      <BarChart data={stockChart.sectorPerformance}>
                        <XAxis dataKey="name" />
                        <YAxis />
                        <Tooltip />
                        <Bar dataKey="performance" fill="hsl(var(--chart-1))" />
                      </BarChart>
                    </ResponsiveContainer>
                  </CardContent>
                </Card>
              </div>
            </div>
          </div>
        </div>
      ) : (
        "Loading..."
      )}
    </p>
  );
}
