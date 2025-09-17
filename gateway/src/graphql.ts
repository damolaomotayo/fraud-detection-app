import { gql } from "apollo-server";

const typeDefs = gql`
  type Transaction {
    id: ID!
    user: String!
    amount: Float!
    timestamp: String!
    fraudulent: Boolean!
  }

  type Query {
    transactions: [Transaction]
    fraudSummary: [Transaction]
  }
`;

export default typeDefs;
