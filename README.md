# httpcomponents-playground

Learning and exploring Apache HTTP Components <https://hc.apache.org/index.html>.

### Instructions

* Run a mock HTTP server with `./gradlew server-wiremock:run`
* Run the client test suite with `./gradlew client:run`

### TODO

* Implement scenario: continuous requests without calling "#close()"
  * How does failure present itself?   