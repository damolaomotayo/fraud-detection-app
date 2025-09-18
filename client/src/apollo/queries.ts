import { gql } from "@apollo/client";

const GET_TRANSACTIONS = gql`
  query {
    fraudSummary {
      id
      user
      amount
      timestamp
      fraudulent
    }
  }
`;

export default GET_TRANSACTIONS;
