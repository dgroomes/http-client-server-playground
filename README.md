# httpcomponents-playground

Learning and exploring Apache HTTP Components <https://hc.apache.org/index.html>.

### Instructions

* Run a mock HTTP server with `./gradlew server-wiremock:run`
* Run the client test suite with `./gradlew client:run`
* Observe the server statistics by going to <http://localhost:8070> in your browser
  * Statistics include things like the number of responses with 200/300/400/500 status codes and the amount of memory 
    used by the server. 

### TODO

* Implement scenario: continuous requests without calling "#close()"
  * How does failure present itself?