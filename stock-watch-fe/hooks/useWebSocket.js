// hooks/useWebSocket.js
import { useEffect, useState, useRef } from "react";

const useWebSocket = (url) => {
  const [messages, setMessages] = useState([]);
  const webSocketRef = useRef(null);

  useEffect(() => {
    // Create WebSocket connection
    webSocketRef.current = new WebSocket(url);

    // Set up WebSocket event listeners
    webSocketRef.current.onmessage = (event) => {
      setMessages((prevMessages) => [...prevMessages, event.data]);
    };

    // Cleanup on unmount
    return () => {
      if (webSocketRef.current) {
        webSocketRef.current.close();
      }
    };
  }, [url]);

  // Function to send a message to the WebSocket server
  const sendMessage = (message) => {
    if (
      webSocketRef.current &&
      webSocketRef.current.readyState === WebSocket.OPEN
    ) {
      webSocketRef.current.send(message);
    }
  };

  return { messages, sendMessage };
};

export default useWebSocket;
