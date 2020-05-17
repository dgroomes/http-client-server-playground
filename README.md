# httpcomponents-playground

Learning and exploring Apache HTTP Components <https://hc.apache.org/index.html>.

---

This project includes example code snippets and a JMeter load test. It is broken down into a collection of sub-projects:
* `client/`
* `client-jmeter-plugin/`
* `client-runner/`
* `server-wiremock/`

### Instructions

Follow these instructions to run a scenario:

1. Run a mock HTTP server with `./gradlew server-wiremock:run`
1. Run the client test suite with `./gradlew client-runner:run`
1. Observe the server statistics by going to <http://localhost:8070/stats/> in your browser
  1. Statistics include things like the number of responses with 200/300/400/500 status codes, the number of connections,
    and the amount of memory used by the server.

### `client-jmeter-plugin/`

This sub-project enables using JMeter to define and execute test plans against the Apache HTTP Components code. It 
builds a JMeter plugin distribution.

Instructions:

1. Run a mock HTTP server with `./gradlew server-wiremock:run`
1. Build the JMeter plugin distribution with `./gradlew :client-jmeter-plugin:installDist`
1. `./run-jmeter-load-test.sh`

Edit the test plan with `./edit-jmeter-load-test.sh`

### TODO

* How can we get the client to fail? How do we leak connections?
  
### Notes

* Check open file descriptors by port `lsof -p 123 | wc -l` 
  * Useful to detect connection leaks (I think)
  * <https://stackoverflow.com/a/38732186>
* Stop JMeter test. It should stop gracefully (but it might still fail!) and produce the test reports
  * `stoptest.sh` This script is in the JMeter installation's `/bin` dir
* Issues relating to the statisticshandler and Jetty async
  * <https://github.com/eclipse/jetty.project/issues/2717>
  * <https://www.eclipse.org/lists/jetty-dev/msg02886.html> 
