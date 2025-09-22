from fastapi.testclient import TestClient
import sys, os
sys.path.append(os.path.join(os.path.dirname(__file__), ".."))
from app import app

client = TestClient(app)

def test_detect_endpoint_returns_predictions():
    payload = [
        {"id": 1, "user": "Alice", "amount": 5000, "timestamp": "2025-09-16T10:30:00Z"},
        {"id": 2, "user": "Bob", "amount": 200, "timestamp": "2025-09-16T14:00:00Z"}
    ]
    resp = client.post("/detect", json=payload)
    assert resp.status_code == 200
    data = resp.json()
    assert isinstance(data, list)
    assert data[0]["id"] == 1
    assert "fraudulent" in data[0]
