export interface Transaction {
  id: string;
  user: string;
  amount: number;
  timestamp: string;
  fraudulent: boolean;
}

export interface TransactionRowProp {
  transaction: Transaction;
}

export interface FraudSummary {
  fraudSummary: Transaction[];
}
