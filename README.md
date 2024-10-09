# Real Time Stock Watch

## Key Features
- Real-time Stock Monitoring: Utilized WebSocket for live price updates and interactions with
  stock market data.
- User Authentication: Secure login with OAuth2, leveraging Keycloak for role-based access
  control.
- Caching: Enhanced performance using Redis for frequently accessed stock data.
- Data Streaming: Used Kafka for consistent data streaming and messaging across
  microservices

## Getting Started

### System Architecture
- Frontend:
- API Gateway(Spring Cloud Gateway)
- Backend Microservices
  - Stock service
  - Order service
  - Payment service
  - Cart service
  - Customer service
- Database:
  - PostgreSQL: Stores structured data for orders, stocks, payments, cart
  - MongoDB: For using notification service
  - Redis: Caches frequently accessed data of stock to minimize latency and reduce database load
- Message System(Kafka)
- Websocket

### Architecture Diagram Example

### Technical Overview
- Backend: Java Spring Boot, Spring Cloud, Kafka, Websocket, Redis, PostgreSQL, MongoDB.
- Frontend
- APIs and Libraries: Resilience4j, Openfeign, Spring Security
- Deployment: Docker

### Executing program

```
code blocks for commands
```


