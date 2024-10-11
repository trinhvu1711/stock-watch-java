import StockPriceWebSocket from "@/components/StockPriceWebSocket";

export default function Home() {
  return (
    <div>
      <h1>Stock Watch</h1>
      <StockPriceWebSocket />
    </div>
  );
}
