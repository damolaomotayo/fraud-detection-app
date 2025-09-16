import { useState, useMemo } from "react";
import type { usePaginationProps } from "../types/pagination-types";
import type { usePaginationReturn } from "../types/pagination-types";

function usePagination<T>({
  items,
  pageSize,
}: usePaginationProps<T>): usePaginationReturn<T> {
  const [currentPage, setCurrentPage] = useState(0);

  const totalPages = Math.ceil(items.length / pageSize);

  const paginatedItems = useMemo(() => {
    const startIndex = currentPage * pageSize;
    return items.slice(startIndex, startIndex + pageSize);
  }, [items, currentPage, pageSize]);

  const hasPrev = currentPage > 0;
  const hasNext = currentPage < totalPages - 1;

  const goToPage = (page: number) => {
    if (page >= 0 && page < totalPages) {
      setCurrentPage(page);
    }
  };

  const goToNext = () => {
    if (hasNext) {
      setCurrentPage((prev) => prev + 1);
    }
  };

  const goToPrev = () => {
    if (hasPrev) {
      setCurrentPage((prev) => prev - 1);
    }
  };

  return {
    currentPage,
    totalPages,
    paginatedItems,
    hasPrev,
    hasNext,
    goToPage,
    goToNext,
    goToPrev,
  };
}

export default usePagination;
