import fetch from "node-fetch";

const resolvers = {
  Query: {
    transactions: async () => {
      const response = await fetch("http://localhost:8080/api/transactions");
      return response.json();
    },
    fraudSummary: async () => {
      const txRes = await fetch("http://localhost:8080/api/transactions");
      const transactions = await txRes.json();

      const fraudRes = await fetch("http://localhost:8000/detect", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(transactions),
      });
      return fraudRes.json();
    },
  },
};

export default resolvers;
