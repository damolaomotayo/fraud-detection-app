import { useEffect, useState } from "react";
import type { Transaction } from "./types";
import TransactionTable from "./components/TransactionTable";
import TransactionChart from "./components/TransactionChart";
import FraudAlerts from "./components/FraudAlerts";

const App = () => {
  const [transactions, setTransactions] = useState<Transaction[]>([]);

  useEffect(() => {
    fetch("/transaction.json")
      .then((response) => response.json())
      .then((data) => setTransactions(data))
      .catch((error) => console.error("Error fetching transactions:", error));
  }, []);

  return (
    <div className="p-6 grid grid-cols-1 md:grid-cols-3 gap-6">
      <div className="md:col-span-2">
        <TransactionTable transactions={transactions} />
        <div className="mt-6 text-sm text-gray-500">
          <TransactionChart transactions={transactions} />
        </div>
      </div>
      <FraudAlerts transactions={transactions} />
    </div>
  );
};

export default App;
