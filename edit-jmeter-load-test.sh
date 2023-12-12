#!/usr/bin/env bash
# Edit the JMeter load test

assertJavaVersion21() {
  # `java --full-version` will print one line to standard out containing a prefix and the "Version String". See https://openjdk.java.net/jeps/223
  # There's a regular expression in the document, but it is not practical to use. Instead, we are simply interested in
  # the Java MAJOR version. Java releases follow the MAJOR.MINOR.SECURITY pattern EXCEPT for early access releases which
  # include only the MAJOR version and a postamble like "-ea+28-1366". So we will relatively safely just try to match the
  # first uninterrupted string of digits we find and take that as the MAJOR version.
  VERSION_OUTPUT=$("$JAVA_HOME/bin/java" --full-version)
  if [[ $VERSION_OUTPUT =~ ([0-9]+) ]]; then
    local major=${BASH_REMATCH[1]}
    if [[ $major != 21 ]]; then
      echo >&2 "Requires Java 21 but found Java $major"
      exit 1
    fi
  else
    echo >&2 "Did not recognize a version string from the 'java --full-version' output: $VERSION_OUTPUT"
    exit 1
  fi
}

assertJavaVersion21

jmeter --testfile test-plan.jmx
