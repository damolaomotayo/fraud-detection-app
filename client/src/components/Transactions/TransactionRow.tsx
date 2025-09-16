import type { TransactionRowProp } from "../../types/transaction-types";

const TransactionRow: React.FC<TransactionRowProp> = ({ transaction }) => (
  <tr className="border-t">
    <td className="p-2">{transaction.id}</td>
    <td className="p-2">{transaction.user}</td>
    <td className="p-2">${transaction.amount}</td>
    <td className="p-2">{new Date(transaction.timestamp).toLocaleString()}</td>
    <td
      className={`p-2 ${
        transaction.fraudulent ? "text-red-600 font-bold" : "text-green-600"
      }`}
    >
      {transaction.fraudulent ? "Yes" : "No"}
    </td>
  </tr>
);
export default TransactionRow;
