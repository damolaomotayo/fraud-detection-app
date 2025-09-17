import { ApolloServer } from "apollo-server";
import typeDefs from "./graphql";
import resolvers from "./resolvers";

const server = new ApolloServer({ typeDefs, resolvers });

server.listen({ port: 4000 }).then(({ url }) => {
  console.log(`🚀 Gateway ready at ${url}`);
});
