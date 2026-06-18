# Getting started

This page shows how to turn a Spring Boot service into a jEAP **TestAgent** by implementing the
`TestAgentOperations` interface from `jeap-bptestagent-api`. For the bigger picture see
[Architecture](architecture.md); for the full contract see the [TestAgent API](testagent-api.md).

## 1. Add the dependency

```xml
<dependency>
    <groupId>ch.admin.bit.jeap</groupId>
    <artifactId>jeap-bptestagent-api</artifactId>
    <version>major.minor.patch</version>
</dependency>
```

The library pulls in `jeap-spring-boot-swagger-starter` (OpenAPI annotations) and
`spring-boot-starter-validation`. Your TestAgent provides the web server (Spring MVC) and any
persistence it needs to remember per-test state.

## 2. Implement `TestAgentOperations`

Implement the interface in a `@RestController`. The interface already carries the Spring Web mappings
(`/api/tests/{testId}` and sub-paths), so you only override the methods. The orchestrator calls these
operations; you do not call them yourself.

```java
@RestController
@RequiredArgsConstructor
@Slf4j
class TestAgentController implements TestAgentOperations {

    private final OrderTestAgentService service;

    @Override
    public ResponseEntity<PreparationResultDto> prepare(String testId, PreparationDto preparationDto) {
        // Remember callbackBaseUrl for this testId, set up fixtures for preparationDto.getTestCase()
        service.prepare(testId, preparationDto);
        return ResponseEntity.ok(PreparationResultDto.builder().build());
    }

    @Override
    public ResponseEntity<ActionResultDto> act(String testId, ActionDto actionDto) {
        service.act(testId, actionDto.getAction());
        return ResponseEntity.ok(ActionResultDto.builder().build());
    }

    @Override
    public ResponseEntity<ReportDto> verify(String testId) {
        return ResponseEntity.ok(service.verify(testId));
    }

    @Override
    public void update(String testId, DynamicDataDto dynamicDataDto) {
        // Optional: store dynamic data the orchestrator collected from other agents
    }

    @Override
    public void cleanUp(String testId) {
        service.cleanUp(testId);
    }
}
```

Methods you have no use case for can simply return an empty `*ResultDto` or do nothing.

## 3. Keep the `callbackBaseUrl`

The `prepare` call delivers a `PreparationDto` that contains `callbackBaseUrl` — the base URL of the
orchestrator for this test run. Persist it against the `testId`; you need it to send
[notifications and logs](orchestrator-callbacks.md) back to the orchestrator while the process runs.

## 4. Report a verdict in `verify`

When the orchestrator asks for the result, return a `ReportDto` containing one `ResultDto` per
checked assertion, each with a `Conclusion` of `PASS` or `FAIL`. See
[Data transfer objects](data-transfer-objects.md) for the field-by-field reference.

```java
ReportDto.builder()
        .testcase("OrderBillingHappyPath")
        .testId(testId)
        .dateTime(ZonedDateTime.now())
        .result(ResultDto.builder()
                .name("Order closed")
                .conclusion(Conclusion.PASS)
                .detail("Order reached state CLOSED")
                .build())
        .build();
```

## Related

- [Architecture](architecture.md)
- [TestAgent API contract](testagent-api.md)
- [Data transfer objects](data-transfer-objects.md)
- [Orchestrator callbacks](orchestrator-callbacks.md)
