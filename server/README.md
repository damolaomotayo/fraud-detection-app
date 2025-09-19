# Transaction Service

The transaction service is a spring boot Rest API responsible for storing and retrieving transaction data.

It acts as the system of record for the fraud detection platform and integrates with:

- Fraud Detection Service - marks suspicious transactions.
- Gateway - aggregates and exposes a signle graphql API.
- React Frontend - displays transactions, fraud alerts, and analytics.

## Features

- REST API for managing transactions.
- Stores transactions in PostgreSQL
- Supports CRUD Operations (GET, POST)
- Integrate with gateway

## Requirements

- Java 17+
- Maven 3.8+
- PostgreSQL

## Setup & Installation

1. Clone the repository

```bash
git clone https://github.com/damolaomotayo/fraud-detection-app.git
cd fraud-detection-app/server
```

2. Set up PostgreSQL and configure application.properties file

```bash
spring.datasource.url=jdbc:postgresql://localhost/db
spring.datasource.username=postgres #change to your postgres username
spring.datasource.password=password # change to your postgres password
```

3. Run the Service

```bash
mvn spring-boot:run
```

The API will run at http://localhost:8080/api/transactions

## API Endpoints

### GET /api/transactions

Fetch all transactions

**Response Example**

```json
[
  {
    "id": 1,
    "user": "Alice",
    "amount": 1200.0,
    "timestamp": "2025-09-16T10:15:00",
    "fraudulent": false
  }
]
```

### POST /api/transactions

create a new transaction.

**Request example**

```json
{
  "user": "Bob",
  "amount": 5000,
  "timestamp": "2025-09-16T10:30:00",
  "fraudulent": false
}
```

**Response Example**

```json
{
  "id": 2,
  "user": "Bob",
  "amount": 5000.0,
  "timestamp": "2025-09-16T10:30:00",
  "fraudulent": false
}
```

## Integration Flow

- Frontend queries GraphQL - Gateway - Transaction Service
- Fraud service enriches transactions - marks fraud
- Database stores transaction history

## Testing

Run tests: `mvn test`
