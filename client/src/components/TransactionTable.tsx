import type { Transaction } from "../types";

interface Props {
  transactions: Transaction[];
}

const TransactionTable: React.FC<Props> = ({ transactions }) => (
  <div className="bg-white shadow rounded-2xl p-4">
    <h2 className="text-xl font-semibold mb-2">Transaction Feed</h2>
    <table className="w-full text-left border-collapse">
      <thead>
        <tr className="bg-gray-100">
          <th className="p-2">ID</th>
          <th className="p-2">User</th>
          <th className="p-2">Amount</th>
          <th className="p-2">Time</th>
          <th className="p-2">Fraud?</th>
        </tr>
      </thead>
      <tbody>
        {transactions.map((tx) => (
          <tr key={tx.id} className="border-t">
            <td className="p-2">{tx.id}</td>
            <td className="p-2">{tx.user}</td>
            <td className="p-2">${tx.amount}</td>
            <td className="p-2">{new Date(tx.timestamp).toLocaleString()}</td>
            <td
              className={`p-2 ${
                tx.fraudulent ? "text-red-600 font-bold" : "text-green-600"
              }`}
            >
              {tx.fraudulent ? "Yes" : "No"}
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  </div>
);

export default TransactionTable;
