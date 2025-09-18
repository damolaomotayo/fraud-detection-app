import { ApolloProvider } from "@apollo/client/react";
import client from "./apollo/client";
import Dashboard from "./components/dashboard/Dashboard";

const App = () => {
  // const [transactions, setTransactions] = useState<Transaction[]>([]);

  // useEffect(() => {
  //   fetch("/transaction.json")
  //     .then((response) => response.json())
  //     .then((data) => setTransactions(data))
  //     .catch((error) => console.error("Error fetching transactions:", error));
  // }, []);

  return (
    <ApolloProvider client={client}>
      <Dashboard />
    </ApolloProvider>
  );
};

export default App;
