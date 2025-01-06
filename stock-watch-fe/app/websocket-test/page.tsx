// components/StockWebSocket.tsx
"use client";

import React, { useState } from "react";
import { Client, IMessage } from "@stomp/stompjs";
import { StocksPriceGetVm } from "@/components/stock/models/StockPriceVm";
import { Button } from "@/components/ui/button";

const StockWebSocket: React.FC = () => {
  const [client, setClient] = useState<Client | null>(null);
  const [messages, setMessages] = useState<StocksPriceGetVm[]>([]);

  const connect = () => {
    const stompClient = new Client({
      webSocketFactory: () => new WebSocket("ws://localhost:8222/websocket"),
    });

    stompClient.onConnect = (frame) => {
      console.log("Connected:", frame);
      stompClient.subscribe("/topic/stock-price", (message: IMessage) => {
        const stockData: StocksPriceGetVm = JSON.parse(message.body);
        setMessages((prevMessages) => [stockData, ...prevMessages]);
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
    }
    setClient(null);
  };

  return (
    <div className="container" id="main-content">
      <div className="row">
        <div className="col-md-10">
          <Button className="btn btn-primary" onClick={() => connect()}>
            Connect WebSocket
          </Button>
          <Button
            className="btn btn-danger"
            onClick={disconnect}
            disabled={!client}
          >
            Disconnect
          </Button>
        </div>
      </div>
      <div className="row">
        <div className="col-md-12">
          <table className="table">
            <thead>
              <tr>
                <th>Time</th>
                <th>Open Price</th>
                <th>Close Price</th>
                <th>High Price</th>
                <th>Low Price</th>
                <th>Volume</th>
              </tr>
            </thead>
            <tbody>
              {messages.map((msg, index) => (
                <tr key={index}>
                  <td>{new Date(msg.timestamp).toLocaleTimeString()}</td>
                  <td>{msg.openPrice}</td>
                  <td>{msg.closePrice}</td>
                  <td>{msg.highPrice}</td>
                  <td>{msg.lowPrice}</td>
                  <td>{msg.volume}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default StockWebSocket;
