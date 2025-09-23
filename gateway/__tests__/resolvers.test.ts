import nock from "nock";
import { ApolloServer, gql } from "apollo-server";
import { makeExecutableSchema } from "@graphql-tools/schema";
import typeDefs from "../src/graphql";
import resolvers from "../src/resolvers";

describe("GraphQL resolvers", () => {
  let server: ApolloServer;

  beforeAll(() => {
    // mock transaction service
    nock("http://localhost:8080")
      .get("/api/transactions")
      .reply(200, [
        {
          id: "1",
          user: "Alice",
          amount: 5000,
          timestamp: "2025-09-16T10:30:00Z",
          fraudulent: false,
        },
      ]);

    // mock fraud service
    nock("http://localhost:8000")
      .post("/detect")
      .reply(200, [
        {
          id: "1",
          user: "Alice",
          amount: 5000,
          timestamp: "2025-09-16T10:30:00Z",
          fraudulent: true,
        },
      ]);

    const schema = makeExecutableSchema({ typeDefs, resolvers });
    server = new ApolloServer({ schema });
  });

  afterAll(async () => {
    await server.stop();
    nock.cleanAll();
  });

  it("returns fraudSummary with enriched fraudulent field", async () => {
    const result = await server.executeOperation({
      query: gql`
        query {
          fraudSummary {
            id
            user
            amount
            fraudulent
          }
        }
      `,
    });
    expect(result.errors).toBeUndefined();
    expect(result.data?.fraudSummary).toBeDefined();
    expect(result.data?.fraudSummary[0].fraudulent).toBe(true);
  });
});
