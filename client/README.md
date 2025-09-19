# Fraud Detection Client (React + TypeScript)

The Fraud Detection Client is a React + TypeScript web dashboard that allows users to

- view transactions
- see fraud alerts flagged by the ML model.
- visualize transaction volumen over time
  It connects to the GraphQL Gateway, which aggregates data from the Transaction Service and Fraud Service.

## Features

- Transaction Table - Displays all transactions
- Fraud Alerts Panel - Highlights suspicious transactions (fraudulent)
- Transaction Volume Chart - line chart of transaction amounts over time
- GraphQl integration - queries data via apollo client from the gateway
- TypeScript for type safety
- TailwindCSS for styling

## Setup & Installation

1. Clone the repository

```bash
git clone https://github.com/damolaomotayo/fraud-detection-app.git
cd fraud-detection-app/client
```

2. Install dependencies

```bash
npm install
```

3. Run development server

```bash
npm run dev
```

frontend runs at http://localhost:5173

## Dependencies

- react - react core.
- typeScript - type safety
- @apollo/client, graphql - graphql integration
- recharts - Charting library
- tailwindCSS - styling

## Integration flow

1. Frontend(React) queries fraudSummary from gateway
2. Gateway calls:
   - Transaction service - fetch transactions
   - fraud service - mark fraud
3. Frontend displays enriched transactions
