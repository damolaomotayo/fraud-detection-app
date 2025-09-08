import React from "react";
import type { Transaction } from "../types";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid,
  ResponsiveContainer,
} from "recharts";

interface Props {
  transactions: Transaction[];
}

const TransactionChart: React.FC<Props> = ({ transactions }) => {
  const data = transactions.map((tx) => ({
    time: new Date(tx.timestamp).toLocaleTimeString(),
    amount: tx.amount,
  }));

  return (
    <div className="bg-white shadow rounded-2xl p-4">
      <h2 className="text-xl font-semibold mb-2">Transaction Volume</h2>
      <ResponsiveContainer width="100%" height={300}>
        <LineChart data={data}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="time" />
          <YAxis />
          <Tooltip />
          <Line type="monotone" dataKey="amount" stroke="#2563eb" />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
};

export default TransactionChart;
