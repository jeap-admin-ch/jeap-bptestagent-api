# Orchestrator callbacks

While the [`TestAgentOperations`](testagent-api.md) interface lets the orchestrator drive the agent,
a TestAgent also reports back to the orchestrator asynchronously while the business process runs. The
library provides the two DTOs for this — `NotificationDto` and `LogDto` — but the agent makes the HTTP
calls itself (this library ships no client). The orchestrator's own endpoints are not part of this
library; they are documented here only because an agent depends on them.

## Where to send callbacks

The orchestrator passes its base URL in `PreparationDto.callbackBaseUrl` during `prepare`. Persist it
against the `testId` and use it to build the callback URLs:

| Callback     | HTTP | URL                                              | Body              |
|--------------|------|--------------------------------------------------|-------------------|
| Notification | POST | `{callbackBaseUrl}/api/tests/{testId}/notifications` | `NotificationDto` |
| Log          | POST | `{callbackBaseUrl}/api/tests/{testId}/logs`          | `LogDto`          |

## Notifications

A notification tells the orchestrator that an event, state or progress step has been reached in the
business process. This lets the orchestrator react (e.g. trigger the next `act`) and follow the test's
progress without polling the agents.

```java
NotificationDto notification = NotificationDto.builder()
        .testId(testId)
        .notification("OrderClosed")
        .producer("Order TestAgent")
        .data("orderId", orderId)
        .build();

restClient.post()
        .uri(callbackBaseUrl + "/api/tests/" + testId + "/notifications")
        .contentType(MediaType.APPLICATION_JSON)
        .body(notification)
        .retrieve()
        .toBodilessEntity();
```

## Logs

A log callback forwards a single log line to the orchestrator, which logs it 1:1 and persists it in
its database alongside the test run. This gives a consolidated view of all agents' progress.

```java
LogDto log = LogDto.builder()
        .logMessage("polling order state...")
        .logLevel(LogLevel.INFO)
        .source("Order TestAgent")
        .build();

restClient.post()
        .uri(callbackBaseUrl + "/api/tests/" + testId + "/logs")
        .contentType(MediaType.APPLICATION_JSON)
        .body(log)
        .retrieve()
        .toBodilessEntity();
```

`LogLevel` is Spring Boot's `org.springframework.boot.logging.LogLevel`.

## Related

- [Architecture](architecture.md)
- [TestAgent API contract](testagent-api.md)
- [Data transfer objects](data-transfer-objects.md)
- [Getting started](getting-started.md)
