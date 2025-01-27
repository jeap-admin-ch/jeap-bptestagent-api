# jeap-bptestagent-api

This library provides an interface for implementing a rest interface in a TestAgent or Simulator towards the orchestrator.

In addition, the necessary Data Transfer Object are provided

### REST API:

|               | OPERATION | Ressource                       | RequestBody    | Response             |
| ------------- | --------- | ------------------------------- | -------------- | -------------------- |
| prepare       | PUT       | /api/tests/<testId>             | PreparationDto | PreparationResultDto |
| update        | PUT       | /api/tests/<testId>/dynamicdata | DynamicDataDto | -                    |
| act           | POST      | /api/tests/<test_id>/actions    | ActionDto      | ActionResultDto      |
| verify        | GET       | /api/tests/<test_id>/report     | -              | ReportDto            |
| cleanUp       | DELETE    | /api/tests/<testId>             | -              | -                    |

### Additional Dto's:

* NotificationDto for notify the Orchestrator
* LogDto for sending simple Log Messages to the Orchestrator

### Integrate

Add this dependency to your POM in the TestAgent

```
<dependency>
    <groupId>ch.admin.bit.jeap</groupId>
    <artifactId>jeap-bptestagent-api</artifactId>
    <version>major.minor.patch</version>
</dependency>
```

## Note

This repository is part the open source distribution of jEAP. See [github.com/jeap-admin-ch/jeap](https://github.com/jeap-admin-ch/jeap)
for more information.

## License

This repository is Open Source Software licensed under the [Apache License 2.0](./LICENSE).
