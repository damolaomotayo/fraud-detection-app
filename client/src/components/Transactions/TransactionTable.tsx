import type { Transaction } from "../../types/transaction-types";
import usePagination from "../../hooks/usePagination";
import TransactionRow from "./TransactionRow";
import PaginationControls from "../PaginationControl/PaginationControls";

interface Props {
  transactions: Transaction[];
}

const TransactionTable: React.FC<Props> = ({ transactions }) => {
  const pageSize = 5;
  const {
    currentPage,
    totalPages,
    paginatedItems: paginated,
    hasPrev,
    hasNext,
    goToNext,
    goToPrev,
  } = usePagination({ items: transactions, pageSize });

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
            <TransactionRow key={tx.id} transaction={tx} />
          ))}
        </tbody>
      </table>

      <PaginationControls
        currentPage={currentPage}
        totalPages={totalPages}
        hasPrev={hasPrev}
        hasNext={hasNext}
        onPrev={goToPrev}
        onNext={goToNext}
      />
    </div>
  );
};

export default TransactionTable;
