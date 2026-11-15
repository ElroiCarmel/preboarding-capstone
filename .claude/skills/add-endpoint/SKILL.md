---
name: add-endpoint
description: Adds a new REST endpoint to this Spring Boot project with tests. Use when asked to add or change an API endpoint.
---

# Add an endpoint

1. Read the existing controller and tests to match conventions.
2. Write a WebMvcTest test for the new endpoint first, including error cases and status codes.
3. Implement the endpoint. Keep business logic out of the controller.
4. Run ./mvnw test and fix failures until all pass.
5. Summarize the change and list the files you touched.

## Rules
- Tests use @WebMvcTest, @AutoConfigureRestTestClient and RestTestClient (not MockMvc).
- Mock dependencies with @MockitoBean. Never hit a real database in a @WebMvcTest.
- Each test creates its own data. Never rely on preloaded data or hardcoded ids.