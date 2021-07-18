plugins {
    /*
     * Using the Gradle "platform" feature to declare dependency version constraints that will be used by the other sub-projects
     * so that we only ever have to declare version information in one place instead of across all sub-projects. See the
     * Gradle docs about "platform": https://docs.gradle.org/current/userguide/platforms.html
     */
    `java-platform`
}

val slf4jVersion = "1.7.30" // releases: http://www.slf4j.org/news.html
val wireMockVersion = "2.26.3" // releases: https://github.com/tomakehurst/wiremock/tags
val httpComponentsV4Version = "4.5.12" // releases: https://hc.apache.org/news.html
val httpComponentsV5Version = "5.1" // releases: https://hc.apache.org/news.html

dependencies {
    constraints {
        api("org.slf4j:slf4j-api:$slf4jVersion")
        api("org.slf4j:slf4j-simple:$slf4jVersion")
        api("org.slf4j:jcl-over-slf4j:$slf4jVersion")
        api("org.apache.httpcomponents:httpclient:$httpComponentsV4Version")
        api("org.apache.httpcomponents.client5:httpclient5:$httpComponentsV5Version")
        api("com.github.tomakehurst:wiremock-jre8:$wireMockVersion")
    }
}
