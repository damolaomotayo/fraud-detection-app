import { useState } from "react";
import type { Transaction } from "../types";

interface Props {
  transactions: Transaction[];
}

const TransactionTable: React.FC<Props> = ({ transactions }) => {
  const [currentPage, setCurrentPage] = useState(0);
  const pageSize = 5;

  const totalPages = Math.ceil(transactions.length / pageSize);
  const startIndex = currentPage * pageSize;
  const paginated = transactions.slice(startIndex, startIndex + pageSize);

  const hasPrev = currentPage > 0;
  const hasNext = currentPage < totalPages - 1;

  console.log("Current Page:", currentPage);
  console.log("Total Page:", totalPages);
  console.log("Has Prev:", hasPrev);
  console.log("Has Next:", hasNext);
  console.log("startIndex:", startIndex);

  return (
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
          {paginated.map((tx) => (
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

      <div className="flex justify-between mt-4">
        <button
          onClick={() => setCurrentPage((p) => p - 1)}
          disabled={!hasPrev}
          className={`px-4 py-2 rounded ${
            hasPrev
              ? "bg-blue-500 text-white hover:bg-blue-600"
              : "bg-gray-300 text-gray-500 cursor-not-allowed"
          }`}
        >
          Prev
        </button>

        <span className="text-sm text-gray-600">
          Page {currentPage + 1} of {totalPages}
        </span>

        <button
          onClick={() => setCurrentPage((p) => p + 1)}
          disabled={!hasNext}
          className={`px-4 py-2 rounded-lg ${
            hasNext
              ? "bg-blue-600 text-white hover:bg-blue-700"
              : "bg-gray-300 text-gray-600 cursor-not-allowed"
          }`}
        >
          Next
        </button>
      </div>
    </div>
  );
};

export default TransactionTable;
