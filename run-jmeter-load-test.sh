#!/usr/bin/env bash
# Run the JMeter load test and once it is finished open the reporting in the browser.
# The script will first delete existing test run output files (i.e. the .jtl file and test report directory) before
# starting the test.

set -eu

assertJavaVersion17() {
  # `java --full-version` will print one line to standard out containing a prefix and the "Version String". See https://openjdk.java.net/jeps/223
  # There's a regular expression in the document, but it is not practical to use. Instead, we are simply interested in
  # the Java MAJOR version. Java releases follow the MAJOR.MINOR.SECURITY pattern EXCEPT for early access releases which
  # include only the MAJOR version and a postamble like "-ea+28-1366". So we will relatively safely just try to match the
  # first uninterrupted string of digits we find and take that as the MAJOR version.
  VERSION_OUTPUT=$("$JAVA_HOME/bin/java" --full-version)
  if [[ $VERSION_OUTPUT =~ ([0-9]+) ]]; then
    local major=${BASH_REMATCH[1]}
    if [[ $major != 17 ]]; then
      echo >&2 "Requires Java 17 but found Java $major"
      exit 1
    fi
  else
    echo >&2 "Did not recognize a version string from the 'java --full-version' output: $VERSION_OUTPUT"
    exit 1
  fi
}

assertJavaVersion17

if [[ ! -f client-jmeter-plugin/build/install/client-jmeter-plugin/client-jmeter-plugin.jar ]]; then
  echo >&2 "The JMeter plugin (the 'client-jmeter-plugin' project) needs to be built before running the JMeter test. Please see the README for instructions."
  exit 1
fi

if [[ -f log.jtl ]]; then
  rm log.jtl
fi

if [[ -d test-report ]]; then
  rm -rf test-report
fi

export JVM_ARGS="-Dnashorn.args=--no-deprecation-warning --enable-preview"
# Run with JVM debug
#export JVM_ARGS="-Dnashorn.args=--no-deprecation-warning --enable-preview -agentlib:jdwp=transport=dt_socket,server=n,address=localhost:5005,suspend=y"
jmeter -n \
  -t test-plan.jmx \
  -l log.jtl \
  -e \
  -o test-report \
  -Lorg.apache.jmeter.protocol.java.sampler=DEBUG

open test-report/index.html
