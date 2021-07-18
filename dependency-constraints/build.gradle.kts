plugins {
    /*
     * Using the Gradle "platform" feature to declare dependency version constraints that will be used by the other sub-projects
     * so that we only ever have to declare version information in one place instead of across all sub-projects. See the
     * Gradle docs about "platform": https://docs.gradle.org/current/userguide/platforms.html
     */
    `java-platform`
}

val slf4jVersion = "1.7.30" // Make sure to use the version that is bundled with JMeter!
val jmeterVersion =
    "5.4.1" // releases: https://jmeter.apache.org/changes_history.html AND https://github.com/apache/jmeter/releases
val wireMockVersion = "2.29.1" // releases: https://github.com/tomakehurst/wiremock/tags
val httpComponentsClientV4Version =
    "4.5.12" // Make sure to use the version that is bundled with JMeter! https://github.com/apache/jmeter/blob/rel/v5.4.1/gradle.properties#L91
val httpComponentsCoreV4Version =
    "4.4.13" // Make sure to use the version that is bundled with JMeter! https://github.com/apache/jmeter/blob/rel/v5.4.1/gradle.properties#L93
val httpComponentsV5Version = "5.1" // releases: https://hc.apache.org/news.html

dependencies {

    constraints {
        api("org.apache.jmeter:ApacheJMeter_java:$jmeterVersion")
        api("org.apache.httpcomponents.client5:httpclient5:$httpComponentsV5Version")
        api("com.github.tomakehurst:wiremock-jre8:$wireMockVersion")
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
