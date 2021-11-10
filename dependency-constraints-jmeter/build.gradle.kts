// This is a Gradle "platform" module that declares dependency constraints. In particular, it defines strict version
// constraints that replicate the exact versions of libaries found in JMeter. When developing a JMeter plugin it's
// important to use the exact versions of libraries already used in JMeter otherwise there might be incompatibility
// issues.
//
// See the Gradle docs about the "platform" feature: https://docs.gradle.org/current/userguide/platforms.html

plugins {
    `java-platform`
}

// JMeter releases: https://jmeter.apache.org/changes_history.html AND https://github.com/apache/jmeter/tags
val jmeterVersion = "5.4.1"
val slf4jVersion = "1.7.30"
val httpComponentsClientV4Version = "4.5.12" //  https://github.com/apache/jmeter/blob/rel/v5.4.1/gradle.properties#L91
val httpComponentsCoreV4Version = "4.4.13" // https://github.com/apache/jmeter/blob/rel/v5.4.1/gradle.properties#L93

dependencies {

    constraints {
        api("org.apache.jmeter:ApacheJMeter_java:$jmeterVersion")
    }

    // Declare strict constraints over the dependencies bundled into JMeter itself. These are hard and fast versions. There
    // is no way to unbundle these dependencies from JMeter and bundle in different versions (well technically you could
    // figure something out but that's not easy and would be tenuous). In other words, these dependencies can be used
    // by our own JMeter plugins but these exact versions must be used.
    constraints {
        api("org.slf4j:slf4j-api") {
            version {
                strictly(slf4jVersion)
            }
        }
        api("org.slf4j:slf4j-simple") {
            version {
                strictly(slf4jVersion)
            }
        }
        api("org.slf4j:jcl-over-slf4j") {
            version {
                strictly(slf4jVersion)
            }
        }
        api("org.apache.httpcomponents:httpclient") {
            version {
                strictly(httpComponentsClientV4Version)
            }
        }
        api("org.apache.httpcomponents:httpcore") {
            version {
                strictly(httpComponentsCoreV4Version)
            }
        }
    }
}
