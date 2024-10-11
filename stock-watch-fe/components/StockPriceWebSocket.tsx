"use client";

import useWebSocket from "@/hooks/useWebSocket";

const StockPriceWebSocket = () => {
  const { messages } = useWebSocket("ws://localhost:8222/ws-stock-updates");

  return (
    <div>
      <h1>Real-time Stock Watch</h1>

      <div>
        {messages && messages.map((msg, index) => <div key={index}>{msg}</div>)}
      </div>
    </div>
  );
};

export default StockPriceWebSocket;
