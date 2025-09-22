import joblib
import os
import pandas as pd
from sklearn.linear_model import LogisticRegression
import pytest

@pytest.fixture(scope="module")
def model_path(tmp_path_factory):
    # train a small model for testing if model.pkl not present
    path = os.path.join(os.path.dirname(__file__), "..", "model.pkl")
    if not os.path.exists(path):
        # simple model
        df = pd.DataFrame({
            "amount": [50, 200, 500, 1200, 3000, 5000, 8000, 50, 100, 7000],
            "hour":   [10, 14, 9, 20, 23, 2, 1, 12, 15, 3],
            "fraudulent": [0,0,0,1,1,1,1,0,0,1]
        })
        X = df[["amount","hour"]]
        y = df["fraudulent"]
        m = LogisticRegression()
        m.fit(X, y)
        joblib.dump(m, path)
    return path

def test_model_predict(model_path):
    model = joblib.load(model_path)
    pred = model.predict([[5000, 10]])
    assert pred.shape == (1,)
    assert pred[0] in (0,1)
