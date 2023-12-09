# http-client-server-playground

📚 Learning and exploring various HTTP client and servers in the JVM ecosystem: Netty, Jetty, Apache HTTP Components.

NOTE: This is currently being re-tooled as an "http-client-server-playground". See the Wish List section. This is a work-in-progress.


## Structure

This project includes simple usage examples of some Java-based HTTP client libraries, Java-based HTTP servers, and a
JMeter load test. It is broken down into a collection of subprojects:

* `clients/`
  * A collection of simple usage examples of some Java-based HTTP client libraries 
  * `client-httpcomponents-v4`
    * A usage example of [Apache HTTP Components](https://hc.apache.org/index.html) version 4.x.
  * `client-httpcomponents-v5` 
    * A usage example of [Apache HTTP Components](https://hc.apache.org/index.html) version 5.x.
* `client-jmeter-plugin/`
* `client-runner/`
* `servers/`
    * `server-wiremock/`
      * A WireMock mock HTTP server. 
    * `server-spring/`
      * A simple Spring Boot app that serves as a mock HTTP server.
    * `server-netty`
      * A simple Netty server wrapped via the convenient [http4k toolkit](https://github.com/http4k/http4k). 


## Instructions

Follow these instructions to run a scenario:

1. Use Java 17
2. Run a mock HTTP server:
   * ```shell
     ./gradlew servers:server-wiremock:run
     ```
   * Alternatively, run a mock HTTP server with different server tech using one of the following commands.
   * ```shell
     ./gradlew servers:server-spring:run
     ```
   * ```shell
     ./gradlew servers:server-netty:run
     ```
   * ```shell
     ./gradlew servers:server-jetty:run
     ```
3. Execute a scenario for one of the HTTP client libraries:
   * ```shell
     ./gradlew client-runner:run --args 'httpcomponents-v4 single-request false'
     ```
   * Alternatively, try a different scenario and different client library. For example, use the following command.
   * ```shell
     ./gradlew client-runner:run --args 'httpcomponents-v5 multiple-requests true'
     ```
4. Observe the server statistics
   * Open <http://localhost:8070/stats/> in the browser
   * Statistics include things like the number of responses with 200/300/400/500 status codes, the number of connections,
     and the amount of memory used by the server.


## `client-jmeter-plugin/`

This subproject enables using JMeter to define and execute test plans against the Apache HTTP Components code. It 
builds a JMeter plugin distribution.

Follow these instructions to run the JMeter load test:

1. Run a mock HTTP server:
   * ```shell
     ./gradlew servers:server-wiremock:run
     ```
2. Build the JMeter plugin distribution:
   * ```shell
     ./gradlew :client-jmeter-plugin:installDist
     ```
3. Run the load test:
   * ```shell
     ./run-jmeter-load-test.sh
     ```
4. Optionally, edit the test plan:
   * ```shell
     ./edit-jmeter-load-test.sh
     ```
   * And then repeat the earlier step to run the test again, as desired.


## Wish List

General clean-ups, changes and things I wish to implement for this project:

* [x] DONE Add a Jetty server example
* [x] DONE Add a Netty server example
* How can we get the Apache HttpComponents client to fail? How do we leak connections? I am curious to force a leak and
  see how the system (client/server) behaves because connection leaks are a common thing in the real world and I want to
  understand it better.
* [ ] IN PROGRESS Re-tool this repo as a "http-client-server-playground" where multiple kinds of clients (Apache HttpComponents, OkHttp, Java
  standard library's HttpClient, others?) can be tested against multiple kinds of servers (Jetty, Tomcat, Netty, maybe
  Undertow).
  * DONE Organize the clients
  * IN PROGRESS Componentize/organize/modularize (whatever) the JMeter plugin modules. This is a bit tricky because there are
    limited options for getting JMeter plugin and JMeter utility/library code on the classpath. See the [options listed in
    the official docs](https://jmeter.apache.org/usermanual/get-started.html#classpath).
  * SKIP? (The client-runner is ok as is. It already parameterizes the scenario/client types) Create a runner shell script, which let's you pick which client to use, and which "scenarios" and other options.
  * DONE add a Spring Boot based server.
* [x] DONE (latest JMeter supports Java 17 thankfully; I won't go to 21 any time soon) Upgrade to Java 17 but BE WARNED. I've tried this twice (in April 2022 and sometime earlier) and I think JMeter doesn't
  work on Java 17. I get this error when I run the JMeter simulation:
  ```text
  Error generating the report: org.apache.jmeter.report.dashboard.GenerationException: Error while processing samples: Consumer failed with message :Consumer failed with message :Consumer failed with message :Consumer failed with message :Begin size 1 is not equal to fixed size 5
  ```
* [x] DONE Dependency upgrades for late 2023
* [ ] Replace `dependency-constraints` with a TOML-based version catalog. Although consider if the `dependendency-constraints-jmeter`
  actually serves a unique purpose that we can't get from a TOML-based version catalog. Maybe use two different version
  catalogs?
* [ ] Major upgrade to http4k 5.x
* [ ] Major upgrade to WireMock 3.x
* [ ] Upgrade to HttpComponents 5.3.x
* [ ] Consider upgrading to SLF4J 2.x (but the other dependencies, especially JMeter, might not be ready for it)
* [ ] Upgrade Spring Boot to 3.x
* [ ] Upgrade to Gradle 8.5


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
