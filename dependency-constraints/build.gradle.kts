plugins {
    `java-platform`
}

val slf4jVersion = "1.7.32" // SLF4J releases: http://www.slf4j.org/news.html
val httpComponentsV5Version = "5.1.1" // HttpComponents v5 releases: https://hc.apache.org/news.html
val wireMockVersion = "2.31.0" // WireMock releases: https://github.com/tomakehurst/wiremock/tags
val http4kVersion = "4.16.3.0" // http4K releases: https://github.com/http4k/http4k/releases

dependencies {
    constraints {
        api("org.slf4j:slf4j-api:$slf4jVersion")
        api("org.slf4j:slf4j-simple:$slf4jVersion")
        api("org.http4k:http4k-core:version:$http4kVersion")
        api("org.http4k:http4k-server-jetty:$http4kVersion")
        api("com.github.tomakehurst:wiremock-jre8:$wireMockVersion")
        api("org.apache.httpcomponents.client5:httpclient5:$httpComponentsV5Version")
    }
}
