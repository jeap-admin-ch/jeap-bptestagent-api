# Data transfer objects

DTOs use `@Builder` and a forced private no-args constructor for Jackson deserialization. Most are
fully immutable (`@Value`); `PreparationDto` and `NotificationDto` use `@Getter`/`@Builder` instead
and `PreparationDto.data` exposes a `@Setter` for post-construction updates. Most free-form payloads travel in a `Map<String, String> data` field; the
builders expose it via `@Singular("data")`, so entries can be added one at a time
(`.data("key", "value")`) or as a whole map. The keys and values in `data`, and the string names
(`action`, `testCase`, `notification`), are agreed per test case between orchestrator and agent.

## Interface DTOs (orchestrator ↔ agent)

### `PreparationDto` — request body of `prepare`

| Field             | Type                  | Notes                                                                                        |
|-------------------|-----------------------|----------------------------------------------------------------------------------------------|
| `testCase`        | `String`              | Required (`@NonNull`). Name of the test case to prepare.                                     |
| `data`            | `Map<String, String>` | Optional setup data for the agent.                                                           |
| `callbackBaseUrl` | `String`              | Required (`@NonNull`). Orchestrator base URL for callbacks; persist it against the `testId`. |

### `PreparationResultDto` — response body of `prepare`

| Field  | Type                  | Notes                                                 |
|--------|-----------------------|-------------------------------------------------------|
| `data` | `Map<String, String>` | Optional data the agent returns to the orchestrator.  |

### `DynamicDataDto` — request body of `update`

| Field  | Type                  | Notes                                                   |
|--------|-----------------------|---------------------------------------------------------|
| `data` | `Map<String, String>` | Dynamic data the orchestrator pushes into the agent.    |

### `ActionDto` — request body of `act`

| Field    | Type                  | Notes                                            |
|----------|-----------------------|--------------------------------------------------|
| `action` | `String`              | Name of the action the agent should perform.     |
| `data`   | `Map<String, String>` | Optional parameters for the action.              |

### `ActionResultDto` — response body of `act`

| Field  | Type                  | Notes                                               |
|--------|-----------------------|-----------------------------------------------------|
| `data` | `Map<String, String>` | Optional result data returned to the orchestrator.  |

### `ReportDto` — response body of `verify`

| Field      | Type               | Notes                                                  |
|------------|--------------------|--------------------------------------------------------|
| `testcase` | `String`           | Required (`@NonNull`). Name of the verified test case. |
| `testId`   | `String`           | Required (`@NonNull`). The test run id.                |
| `dateTime` | `ZonedDateTime`    | Required (`@NonNull`). When the report was produced.   |
| `results`  | `List<ResultDto>`  | Required (`@NonNull`). One entry per assertion.        |

### `ResultDto` — one assertion within a `ReportDto`

| Field        | Type         | Notes                                                  |
|--------------|--------------|--------------------------------------------------------|
| `name`       | `String`     | Required (`@NonNull`). Name of the checked assertion.  |
| `conclusion` | `Conclusion` | Required (`@NonNull`). `PASS` or `FAIL`.               |
| `detail`     | `String`     | Optional free-text detail (e.g. failure reason).       |

### `Conclusion` — enum

`PASS`, `FAIL`.

## Callback DTOs (agent → orchestrator)

These are sent by the agent to the orchestrator's callback endpoints; see
[Orchestrator callbacks](orchestrator-callbacks.md).

### `NotificationDto`

| Field          | Type                  | Notes                                                       |
|----------------|-----------------------|-------------------------------------------------------------|
| `testId`       | `String`              | Required (`@NotNull`). The test run id.                     |
| `notification` | `String`              | Required (`@NotNull`). Name of the reported event/state.    |
| `producer`     | `String`              | Required (`@NotNull`). Which TestAgent produced it.         |
| `data`         | `Map<String, String>` | Optional payload (e.g. ids produced during the process).    |

`NotificationDto` implements `Serializable`.

### `LogDto`

| Field        | Type       | Notes                                                                           |
|--------------|------------|---------------------------------------------------------------------------------|
| `logMessage` | `String`   | Required (`@NonNull`). The log text.                                            |
| `logLevel`   | `LogLevel` | Required (`@NonNull`). Spring Boot `org.springframework.boot.logging.LogLevel`. |
| `source`     | `String`   | Required (`@NonNull`). Name of the emitting TestAgent.                          |

## Related

- [TestAgent API contract](testagent-api.md)
- [Orchestrator callbacks](orchestrator-callbacks.md)
- [Getting started](getting-started.md)
