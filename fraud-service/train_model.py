import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LogisticRegression
import joblib

data = {
   "amount": [50, 200, 500, 1200, 3000, 5000, 8000, 50, 100, 7000],
    "hour":   [10, 14, 9, 20, 23, 2, 1, 12, 15, 3],
    "fraudulent": [0, 0, 0, 1, 1, 1, 1, 0, 0, 1]
}

df = pd.DataFrame(data)

X = df[["amount", "hour"]]
y = df["fraudulent"]

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

model = LogisticRegression()
model.fit(X_train, y_train)

joblib.dump(model, "model.pkl")
print("Model trained and saved as model.pkl")