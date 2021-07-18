# httpcomponents-playground

NOTE: This is currently being re-tooled as an "http-client-server-playground". See the Wish List section. This is a work-in-progress.

📚 Learning and exploring Apache HTTP Components <https://hc.apache.org/index.html>.

## Structure

This project includes simple usage examples of some Java-based HTTP client libraries, and a JMeter load test. It is broken down
into a collection of sub-projects:

* `clients/`
  * A collection of simple usage examples of some Java-based HTTP client libraries 
  * `client-httpcomponents-v4`
    * A usage example of [Apache HTTP Components](https://hc.apache.org/index.html) version 4.x.
  * `client-httpcomponents-v5` 
    * A usage example of [Apache HTTP Components](https://hc.apache.org/index.html) version 5.x.
* `client-jmeter-plugin/`
* `client-runner/`
* `server-wiremock/`

## Instructions

Follow these instructions to run a scenario:

1. Use Java 16
1. Run a mock HTTP server:
   * `./gradlew server-wiremock:run`
1. Run the client test suite:
   * `./gradlew client-runner:run`
1. Observe the server statistics
   * Open <http://localhost:8070/stats/> in the browser
   * Statistics include things like the number of responses with 200/300/400/500 status codes, the number of connections,
     and the amount of memory used by the server.

## `client-jmeter-plugin/`

This sub-project enables using JMeter to define and execute test plans against the Apache HTTP Components code. It 
builds a JMeter plugin distribution.

Instructions:

1. Run a mock HTTP server with `./gradlew server-wiremock:run`
1. Build the JMeter plugin distribution with `./gradlew :client-jmeter-plugin:installDist`
1. `./run-jmeter-load-test.sh`

Edit the test plan with `./edit-jmeter-load-test.sh`

## Wish List

General clean-ups, changes and things I wish to implement for this project:

* How can we get the Apache HttpComponents client to fail? How do we leak connections? I am curious to force a leak and
  see how the system (client/server) behaves because connection leaks are a common thing in the real world and I want to
  understand it better.
* IN PROGRESS Re-tool this repo as a "http-client-server-playground" where multiple kinds of clients (Apache HttpComponents, OkHttp, Java
  standard library's HttpClient, others?) can be tested against multiple kinds of servers (Jetty, Tomcat, Netty, maybe
  Undertow).
  * DONE Organize the clients
  * IN PROGRESS Componentize/organize/modularize (whatever) the JMeter plugin modules. This is a bit tricky because there are
    limited options for getting JMeter plugin and JMeter utility/library code on the classpath. See the [options listed in
    the official docs](https://jmeter.apache.org/usermanual/get-started.html#classpath).
  * Create a runner shell script, which let's you pick which client to use, and which "scenarios" and other options.

## Notes

* Check open file descriptors by port `lsof -p 123 | wc -l` 
  * Useful to detect connection leaks (I think)
  * <https://stackoverflow.com/a/38732186>
* Stop JMeter test. It should stop gracefully (but it might still fail!) and produce the test reports
  * `stoptest.sh` This script is in the JMeter installation's `/bin` dir
* Issues relating to the statisticshandler and Jetty async
  * <https://github.com/eclipse/jetty.project/issues/2717>
  * <https://www.eclipse.org/lists/jetty-dev/msg02886.html> 

## Reference

* <https://github.com/dgroomes/jmeter-playground>
  * This is my own project that shows, with commentary, how to run JMeter test with a custom plugin and with custom library
    dependencies (i.e. `.jar` files).
