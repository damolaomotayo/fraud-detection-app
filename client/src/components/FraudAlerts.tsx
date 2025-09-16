import type { Transaction } from "../types/transaction-types";
import { AlertTriangle, CheckCircle2 } from "lucide-react";

interface Props {
  transactions: Transaction[];
}

const FraudAlerts: React.FC<Props> = ({ transactions }) => {
  const fraudulent = transactions.filter((tx) => tx.fraudulent);

  return (
    <div className="bg-white shadow rounded-2xl p-4">
      <h2 className="text-xl font-semibold mb-4 flex items-center gap-2">
        <AlertTriangle className="text-red-600" />
        Fraud Alerts
      </h2>

      {fraudulent.length === 0 ? (
        <div className="flex flex-col items-center justify-center py-8 text-center text-green-700">
          <CheckCircle2 size={40} className="mb-2" />
          <p className="text-lg font-medium">No fraud detected 🎉</p>
          <p className="text-sm text-gray-500">All transactions look safe.</p>
        </div>
      ) : (
        <div className="space-y-3">
          {fraudulent.map((tx) => (
            <div
              key={tx.id}
              className="border border-red-200 bg-red-50 rounded-xl p-3 shadow-sm flex justify-between items-center"
            >
              <div>
                <p className="font-semibold text-red-700">
                  Suspicious Transaction
                </p>
                <p className="text-sm text-gray-700">
                  <span className="font-medium">{tx.user}</span> attempted{" "}
                  <span className="text-red-600 font-bold">
                    ${tx.amount.toLocaleString()}
                  </span>
                </p>
                <p className="text-xs text-gray-500">
                  {new Date(tx.timestamp).toLocaleString()}
                </p>
              </div>
              <AlertTriangle className="text-red-600" size={28} />
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default FraudAlerts;
