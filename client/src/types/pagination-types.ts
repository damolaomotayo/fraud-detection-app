export interface usePaginationProps<T> {
  items: T[];
  pageSize: number;
}

export interface usePaginationReturn<T> {
  currentPage: number;
  totalPages: number;
  paginatedItems: T[];
  hasPrev: boolean;
  hasNext: boolean;
  goToPage: (page: number) => void;
  goToNext: () => void;
  goToPrev: () => void;
}

export interface PaginationControlsProps {
  currentPage: number;
  totalPages: number;
  hasPrev: boolean;
  hasNext: boolean;
  onPrev: () => void;
  onNext: () => void;
}
