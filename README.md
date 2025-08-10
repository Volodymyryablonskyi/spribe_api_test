# Players API — Test Automation Framework

Lightweight TestNG/RestAssured framework for **Players Controller API** (create / update / delete / get-by-id / get-all, role-based access).  
Designed to run safely in parallel, produce Allure reports, and keep the test data clean.

---

## Stack

- **Java** 21
- **Maven**
- **TestNG**
- **RestAssured**
- **Jackson / Gson**
- **Allure** (reporting)
- **SLF4J + Logback** (logging)

---

## Project layout (quick map)

```
src/
  main/java/
    assertions/         # PlayerVerifier, PlayersListVerifier
    clients/            # PlayersApiClient (thin HTTP wrapper)
    config/             # RestAssuredConfigurator, config loading
    constants/          # Constants (e.g., BASE_URI)
    endpoints/          # Classes with String ndpoints 
    http/               # Request/response helpers (ResponseWrapper, StatusCode)
    pojo/               # DTOs
    util/               # Randomizer, Sleep, TimeUtil, logging utils

  test/java/
    base/               # BaseTest, BasePlayerTest (ThreadLocal state, cleanup, helpers)
    listeners/          # GlobalTestListener 
    tests/
      access/           # Role access checks
      create/           # Positive/Negative Create
      update/           # Positive/Negative Update
      delete/           # Positive/Negative Delete
      getbyid/          # Positive/Negative GET by id
      getall/           # GET all
  test/resources/
    testng.xml          # Suite (parallel=methods via Surefire)
```

## How to run

### 1) Default run (single thread)
```bash
mvn clean test
```

### 2) Parallel run (e.g., 3 threads)
```bash
mvn clean test -Dthreads=3
```

### 3) Point to another API host
```bash
mvn clean test -Dbase.uri=http://<host-or-ip>:<port>
```

### 4) Combine
```bash
mvn clean test -Dthreads=3 -Dbase.uri=http://3.68.165.45
```

### 5) Run by groups
Common groups include:
- `api`, `regression`
- `api-players`
- `create-positive`, `create-negative`
- `update-positive`, `update-negative`
- `delete-positive`, `delete-negative`
- `get-positive`, `get-negative`, `get-all`, `get-by-id`
- `role-player-access`

Example:
```bash
mvn clean test -Dgroups="create-positive,delete-negative"
```

## Allure report

Generate & open locally after a run:

```bash
allure serve target/allure-results
```

If `serve` isn’t available:
```bash
allure generate target/allure-results -o target/allure-report --clean
allure open target/allure-report
```

> Allure CLI install: brew/choco/scoop or see Allure docs.

---

## Configuration knobs

All settings are exposed via **system properties** (Maven `-D`):

| Property      | What it does                                    | Default (pom)                 |
|---------------|--------------------------------------------------|-------------------------------|
| `base.uri`    | Target API base URL                              | from `application.properties` |
| `threads`     | TestNG/Surefire `threadCount` (parallel=methods) | `3`                           |

Example:
```bash
mvn clean test -Dthreads=3 -Dbase.uri=http://3.68.165.45
```

> DataProviders are kept **non-parallel** to avoid API race conditions. Overall parallelism is controlled by Surefire (methods).

---

## Notes on stability

- Tests auto-clean entities they created (`@AfterMethod` + admin delete), so suites can be re-run safely.
- Some endpoints may return `200`/`204` interchangeably; assertions are tolerant where the spec/impl differs.
- “Eventually consistent” checks use small waiters; see `BasePlayerTest`.

---

## Typical suite command (what we use)

```bash
mvn clean test   -Dthreads=3   -Dbase.uri=http://3.68.165.45
allure serve target/allure-results
```
