## 🛡️ Fraud Detection Service

The Fraud Detection Service is a python FastAPI service that uses a lightweight **Machine Learning(Logistic Regression)** model to detect potentially fraudulent transactions

It is designed to work as part of a Fraud Detection Platform alongside:

- **Transaction Service** (Spring Boot + PostgreSQL)
- **GraphQL Gateway** (Node.js + Apollo)
- **React Frontend** (TypeScript)

## Features

- **REST API(/detect)** that accepts a list of transactions and returns fraud predictions
- **Lightweight ML model(Logistic Regression)** trained on synthetic transaction data.
- predicts fraud based on transaction **amount** and **time of day**
- Easy integration with a **GraphQL Gateway** for aggregation.
- Extensible -- swap in more complex ML models if needed.

## Setup && Installation

1. Clone the repository

```bash
git clone https://github.com/damolaomotayo/fraud-detection-app.git
cd fraud-detection-app/fraud-service
```

2. create a virtual environment

```bash
python3 -m venv venv
source venv/bin/activate # macos/Linux
venv\Scripts\activate # Windows
```

3. Install dependencies

```bash
pip install -r requirements.txt
```

4. Train the model

```bash
python train_model.py
```

This generates a model.pkl file that the service loads for the fraud detection. 5. Start the service

```bash
uvicorn app:app --reload --port 8000
```

API will be available at
https://localhost:8000/docs (swagger UI)
https://localhost:8000/redoc

## API Endpoints

`POST /detect`
predicts fraud for a list of transactions
**Request Body (JSON)**

```json
[
  [
    {
      "id": 1,
      "user": "Alice",
      "amount": 5000,
      "timestamp": "2025-09-16T10:30:00Z",
      "fraudulent": false
    },
    {
      "id": 2,
      "user": "Bob",
      "amount": 200,
      "timestamp": "2025-09-16T14:00:00Z",
      "fraudulent": false
    }
  ]
]
```

**Response**

```json
[
  {
    "id": 1,
    "user": "Alice",
    "amount": 5000.0,
    "timestamp": "2025-09-16T10:30:00Z",
    "fraudulent": true
  },
  {
    "id": 2,
    "user": "Bob",
    "amount": 200.0,
    "timestamp": "2025-09-16T14:00:00Z",
    "fraudulent": false
  }
]
```

## How it works

1. Transactions(id, user, amount, timestamp) are passed to /detect
2. Service extracts features: `amount`, `hour`
3. ML model (Logistic Regression) predicts fraud probability.
4. Returns enriched transactions with `"fraudulent": true/false`

## Integration

- Gateway calls `/detect` to enrich transaction data with fraud predictions.
- Frontend queries gateway and shows fraud alerts
- works seamlessly with java transaction service as the source of truth for transactions
