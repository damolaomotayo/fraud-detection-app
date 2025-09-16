import type { PaginationControlsProps } from "../../types/pagination-types";

const PaginationControls: React.FC<PaginationControlsProps> = ({
  currentPage,
  totalPages,
  hasPrev,
  hasNext,
  onPrev,
  onNext,
}) => (
  <div className="flex justify-between mt-4">
    <button
      onClick={onPrev}
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
      onClick={onNext}
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
);

export default PaginationControls;
