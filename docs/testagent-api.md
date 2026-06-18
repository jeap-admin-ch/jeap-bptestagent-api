# TestAgent API contract

`TestAgentOperations` is the REST interface every TestAgent/simulator implements. The BusinessTest
Orchestrator calls it to drive a test run. All operations are mapped under `/api/tests` and identify
the running test with the `{testId}` path variable. The interface is annotated with OpenAPI/Swagger
annotations (`@Tag`, `@Operation`), so implementing it also publishes the contract in the agent's API
documentation.

## Operations

| Operation | HTTP   | Path                              | Request body     | Response               |
|-----------|--------|-----------------------------------|------------------|------------------------|
| `prepare` | PUT    | `/api/tests/{testId}`             | `PreparationDto` | `PreparationResultDto` |
| `update`  | PUT    | `/api/tests/{testId}/dynamicdata` | `DynamicDataDto` | — (`void`)             |
| `act`     | POST   | `/api/tests/{testId}/actions`     | `ActionDto`      | `ActionResultDto`      |
| `verify`  | GET    | `/api/tests/{testId}/report`      | —                | `ReportDto`            |
| `cleanUp` | DELETE | `/api/tests/{testId}`             | —                | — (`void`)             |

`prepare`, `act` and `verify` return a `ResponseEntity<...>` so the implementation controls the HTTP
status; `update` and `cleanUp` return `void`.

## Lifecycle

The orchestrator typically calls the operations in this order for one `testId`:

1. **prepare** — initialise the agent for a test case. The `PreparationDto` carries the `testCase`
   name, optional setup `data` and the `callbackBaseUrl` the agent must keep for callbacks. The agent
   sets up its fixtures and returns a `PreparationResultDto` (optionally with data for the
   orchestrator).
2. **update** *(optional, repeatable)* — push dynamic data the orchestrator gathered from other agents
   (e.g. an id produced elsewhere) into this agent via `DynamicDataDto`.
3. **act** *(repeatable)* — ask the agent to perform a named `action` (e.g. `submitOrder`). The agent
   executes it and returns an `ActionResultDto`. An action may kick off asynchronous work whose
   completion the agent later reports through a [notification](orchestrator-callbacks.md).
4. **verify** — ask the agent for its verdict. The agent returns a `ReportDto` with one `ResultDto`
   (`PASS`/`FAIL`) per assertion.
5. **cleanUp** — tear down everything the agent created for this `testId`.

The `action`, `testCase` and notification names are agreed per test case between the orchestrator and
the agent; this library does not enumerate them. Implementations usually `switch` on
`preparationDto.getTestCase()` and `actionDto.getAction()`.

## Related

- [Getting started](getting-started.md)
- [Data transfer objects](data-transfer-objects.md)
- [Orchestrator callbacks](orchestrator-callbacks.md)
- [Architecture](architecture.md)
