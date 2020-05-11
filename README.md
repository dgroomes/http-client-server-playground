# httpcomponents-playground

Learning and exploring Apache HTTP Components <https://hc.apache.org/index.html>.

### Instructions

* Run a mock HTTP server with `./gradlew server-wiremock:run`
* Run the client test suite with `./gradlew client:run`
* Observe the server statistics by going to <http://localhost:8070/stats/> in your browser
  * Statistics include things like the number of responses with 200/300/400/500 status codes, the number of connections,
    and the amount of memory used by the server. 

### TODO

* Use connection pooling in the client
  * How can we get the client to fail? How do we leak connections?