# AGENTS.md

Guidance for AI coding agents working **in this repository**. For how to *use* the library when
implementing a TestAgent, read [README.md](README.md) and the [docs/](docs/) folder instead.

> "Agent" here is overloaded: this file is for AI coding agents; the library itself is about jEAP
> **TestAgents** (simulators driven by the BusinessTest Orchestrator).

## Project

`jeap-bptestagent-api` is a small single-module Maven library that defines the REST contract between
the jEAP **BusinessTest Orchestrator** and a **TestAgent** (simulator). It contains one Spring MVC
interface, `TestAgentOperations`, annotated with Spring Web and OpenAPI/Swagger annotations, plus the
request/response and callback DTOs. There is no business logic, no auto-configuration and no
configuration properties — a TestAgent service implements `TestAgentOperations` and provides the
runtime (web server, persistence, callbacks).

## Repository layout

```
pom.xml                                                  # Single-module POM (packaging=jar)
src/main/java/ch/admin/bit/jeap/testagent/api/
  TestAgentOperations.java                               # The REST interface the orchestrator calls
  prepare/PreparationDto.java                            # prepare request body
  prepare/PreparationResultDto.java                      # prepare response body
  update/DynamicDataDto.java                             # update request body
  act/ActionDto.java                                     # act request body
  act/ActionResultDto.java                               # act response body
  verify/ReportDto.java                                  # verify response body
  verify/ResultDto.java                                  # one assertion result within a report
  verify/Conclusion.java                                 # PASS / FAIL enum
  notification/NotificationDto.java                      # agent -> orchestrator notification callback
  notification/LogDto.java                               # agent -> orchestrator log callback
Jenkinsfile, publiccode.yml, CHANGELOG.md, LICENSE, THIRD-PARTY-LICENSES.md
```

## Build & test

```bash
./mvnw verify        # full build incl. license checks
./mvnw -q install    # install the artifact locally
```

- Parent: `ch.admin.bit.jeap:jeap-spring-boot-parent` (Spring Boot 4 aligned).
- Spring Boot 3 maintenance happens on the `release/springboot3` branch; `master` targets Spring Boot 4.
- Dependencies are limited to `jeap-spring-boot-swagger-starter` (OpenAPI annotations) and
  `spring-boot-starter-validation` (`jakarta.validation` constraints on the DTOs).

## jEAP conventions

- Java packages live under `ch.admin.bit.jeap.testagent.api...`, sub-packaged per lifecycle step
  (`prepare`, `update`, `act`, `verify`, `notification`).
- The REST contract is fixed at `@RequestMapping("/api/tests")`; operations key off the
  `{testId}` path variable.
- DTOs use Lombok (`@Value`/`@Getter`, `@Builder`, `@Singular` for the `Map<String,String> data`
  fields) and a forced private no-args constructor so Jackson can deserialize immutable types.
- Free-form payloads travel in `Map<String, String> data`; the `action`, `testCase` and
  `notification` string fields are agreed between orchestrator and agent per test case, not enumerated
  by this library.

## Docs

When changing the contract (an operation, a DTO field, or a callback), update the matching focused
file under [docs/](docs/) (one topic per file) and the documentation index in the README.

## Versioning

- Semantic Versioning; all changes documented in [CHANGELOG.md](./CHANGELOG.md) (Keep a Changelog format).
- `setPomVersions.sh <version>` updates the version in the POM.
- When working on a feature branch, increase the version to `x.y.z-SNAPSHOT` in the POM.
- Always keep the -SNAPSHOT postfix in the POM, CI removes it when releasing a version. Do not use the
  SNAPSHOT postfix in other places (CHANGELOG, publiccode.yml etc).
- Keep changelog entries concise and follow existing patterns.
- Keep commit messages short, use the JIRA ID from the branch name as a prefix, do not use
  conventional commits (for example: "JEAP-1234 Added feature X").
- When bumping the version, also update the changelog and the version/date in `publiccode.yml`.
