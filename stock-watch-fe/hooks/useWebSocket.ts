// hooks/useWebSocket.ts
import { useState, useEffect } from "react";
import { Client, IMessage } from "@stomp/stompjs";

interface StocksPriceGetVm {
  openPrice: number;
  closePrice: number;
  highPrice: number;
  lowPrice: number;
  volume: number;
  timestamp: string;
}

export const useWebSocket = (url: string, topic: string) => {
  const [client, setClient] = useState<Client | null>(null);
  const [messages, setMessages] = useState<StocksPriceGetVm[]>([]);

  const connect = () => {
    const stompClient = new Client({
      webSocketFactory: () => new WebSocket(url),
    });

    stompClient.onConnect = () => {
      console.log("Connected");
      stompClient.subscribe(topic, (message: IMessage) => {
        const stockData: StocksPriceGetVm = JSON.parse(message.body);

        // Only add new message if it’s not the same as the most recent one
        setMessages((prevMessages) => {
          if (
            prevMessages.length > 0 &&
            prevMessages[0].timestamp === stockData.timestamp
          ) {
            return prevMessages;
          }
          return [stockData, ...prevMessages.slice(0, 49)]; // Keep the array to 50 messages max
        });
      });
    };

    stompClient.onStompError = (error) => {
      console.error("Error:", error);
    };

    stompClient.onWebSocketClose = () => {
      console.log("WebSocket closed.");
    };

    stompClient.activate();
    setClient(stompClient);
  };

  const disconnect = () => {
    if (client) {
      client.deactivate();
      setClient(null);
    }
  };

  useEffect(() => {
    return () => {
      disconnect();
    };
  }, []);

  return { messages, connect, disconnect };
};
