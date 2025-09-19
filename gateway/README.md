## GraphQL Gateway

This is a node microservcie that acts as a single API layer for the fraud detection application.

it aggregates data from:

- Transaction Service(Java Sprign Boot)
- Fraud Detection Service (Python FastAPI + ML)
  and exposes a GraphQL API that the frontend can query.

## Features

- provides a unified graphql API across multiple services.
- Aggregates transaction data from spring boot + fraud predictions from python ML service.
- Enables frontend to fetch enriched data with a single query
- Supports flexile queries for scalability

## Setup & Installation

1. Clone the repository

```bash
git clone https://github.com/damolaomotayo/fraud-detection-app.git
cd fraud-detection-app/gateway
```

2. Install dependencies

```bash
npm install
```

3. Development with auto-reload

```bash
npm run dev
```

## Dependencies

- apollo-server - graphql server
- graphql - schema and queries
- node-fetch - rest api calls to backend services
- typescript, ts-node, nodemon - dev tooling

## Configuration

- Transaction Service expected at `http://localhost:8080/api/transactions`
- Fraud Service expected at `http://localhost:8000/detect`
- GraphQL Gateway runs on `http://localhost:4000`

## GraphQL Schema

```graphql
type Transaction {
  id: ID!
  user: String!
  amount: Float!
  timestamp: String!
  fraudulent: Boolean!
}

type Query {
  transactions: [Transaction]
  fraudSummary: [Transaction]
}
```

## API Queries

1. Fetch all transactions

```graphql
query {
  transactions {
    id
    user
    amount
    timestamp
    fraudulent
  }
}
```

2. Fetch transactions with fraud predictions

```graphql
query {
  fraudSummary {
    id
    user
    amount
    fraudulent
  }
}
```

## Integration Flow

1. Frontend queries graphql (http://localhost:4000)
2. Gateway calls:
   - Transaction Service(Spring Boot) - fetches transactions
   - Fraud Service(FastAPI ML) - predicts fraud.
3. Gateway merges responses and returns unified results.
