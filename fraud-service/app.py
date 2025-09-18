from fastapi import FastAPI
from pydantic import BaseModel
from typing import List
import joblib
import datetime

model = joblib.load("model.pkl")

app = FastAPI()

class Transaction(BaseModel):
    id: int
    user: str
    amount: float
    timestamp: str
    fraudulent: bool = False


@app.post("/detect")
def detect_fraud(transactions: List[Transaction]) -> List[Transaction]:
    results = []
    for tx in transactions:
        dt = datetime.datetime.fromisoformat(tx.timestamp.replace("Z", "+00:00"))
        hour = dt.hour
        features = [[tx.amount, hour]]

        prediction = model.predict(features)[0]
        is_fraud = bool(prediction)

        results.append({**tx.dict(), "fraudulent": is_fraud})
    return results