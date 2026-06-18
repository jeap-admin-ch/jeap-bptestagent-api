# jeap-bptestagent-api

`jeap-bptestagent-api` is the API/contract library for jEAP **business process test agents**. A
business process test (BP test) exercises a real business process end-to-end across several services.
A central **BusinessTest Orchestrator** drives the test by calling one or more **TestAgents** (also
called simulators) that stand in for, or interact with, the systems under test. This library defines
the standardized REST contract between the orchestrator and a TestAgent, so a TestAgent
implementation only has to implement an interface and use the provided DTOs. It provides:

* The `TestAgentOperations` REST interface every TestAgent implements (prepare, update, act, verify, clean up)
* The request/response DTOs exchanged on that interface (`PreparationDto`, `ActionDto`, `ReportDto`, ...)
* Callback DTOs a TestAgent sends back to the orchestrator (`NotificationDto`, `LogDto`)
* OpenAPI/Swagger annotations so the contract is published in the TestAgent's API documentation

## Documentation

Start with [Getting started](docs/getting-started.md), then follow the links below.

| Topic                                                | File                                                         |
|------------------------------------------------------|--------------------------------------------------------------|
| Getting started (add the dependency, implement an agent) | [docs/getting-started.md](docs/getting-started.md)       |
| Architecture (orchestrator ↔ TestAgent interaction)  | [docs/architecture.md](docs/architecture.md)                 |
| TestAgent API contract (operations & lifecycle)      | [docs/testagent-api.md](docs/testagent-api.md)               |
| Data transfer objects (DTO reference)                | [docs/data-transfer-objects.md](docs/data-transfer-objects.md) |
| Orchestrator callbacks (notifications & logs)        | [docs/orchestrator-callbacks.md](docs/orchestrator-callbacks.md) |

## Modules

This is a single-module library. The artifact a TestAgent depends on is `jeap-bptestagent-api`; the
group id is `ch.admin.bit.jeap`.

| Module                  | Purpose                                                                                     |
|-------------------------|---------------------------------------------------------------------------------------------|
| `jeap-bptestagent-api`  | The `TestAgentOperations` REST interface and the DTOs exchanged with the orchestrator       |

## Changes

This library is versioned using [Semantic Versioning](http://semver.org/) and all changes are documented in
[CHANGELOG.md](./CHANGELOG.md) following the format defined in [Keep a Changelog](http://keepachangelog.com/).

## Note

This repository is part the open source distribution of jEAP. See [github.com/jeap-admin-ch/jeap](https://github.com/jeap-admin-ch/jeap)
for more information.

## License

This repository is Open Source Software licensed under the [Apache License 2.0](./LICENSE).
