import type { FraudSummary } from "../../types/transaction-types";
import { useQuery } from "@apollo/client/react";
import GET_TRANSACTIONS from "../../apollo/queries";
import FraudAlerts from "../FraudAlerts";
import TransactionChart from "../Transactions/TransactionChart";
import TransactionTable from "../Transactions/TransactionTable";

const Dashboard = () => {
  const { loading, error, data } = useQuery<FraudSummary>(GET_TRANSACTIONS);

  if (loading) return <p>Loading...</p>;
  if (error) {
    console.log("Error fetching transactions:", error);
    return <p>Error: {error.message}</p>;
  }

  const transactions: FraudSummary = data ? data : { fraudSummary: [] };

  if (!transactions || transactions.fraudSummary.length === 0) {
    return <p>No transactions available.</p>;
  }

  return (
    <div className="p-6 grid grid-cols-1 md:grid-cols-3 gap-6">
      <div className="md:col-span-2">
        <TransactionTable transactions={transactions.fraudSummary} />
        <div className="mt-6 text-sm text-gray-500">
          <TransactionChart transactions={transactions.fraudSummary} />
        </div>
      </div>
      <FraudAlerts transactions={transactions.fraudSummary} />
    </div>
  );
};

export default Dashboard;
