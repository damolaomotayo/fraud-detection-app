import type { Transaction } from "../types";

interface Props {
  transactions: Transaction[];
}

const FraudAlerts: React.FC<Props> = ({ transactions }) => {
  const fraudulent = transactions.filter((tx) => tx.fraudulent);

  return (
    <div className="bg-red-50 shadow rounded-2xl p-4">
      <h2 className="text-xl font-semibold mb-2">Fraud Alerts 🚨</h2>
      {fraudulent.length === 0 ? (
        <p>No fraud detected 🎉</p>
      ) : (
        <ul>
          {fraudulent.map((tx) => (
            <li key={tx.id} className="border-b py-1">
              <span className="font-bold">{tx.user}</span> suspicious
              transaction of <span className="text-red-600">${tx.amount}</span>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default FraudAlerts;
