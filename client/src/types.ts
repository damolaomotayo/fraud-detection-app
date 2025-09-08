export interface Transaction {
  id: string;
  user: string;
  amount: number;
  timestamp: string;
  fraudulent: boolean;
}
