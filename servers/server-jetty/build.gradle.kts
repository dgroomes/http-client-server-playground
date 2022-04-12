plugins {
    id("common")
    kotlin("jvm") version "1.6.10" // Kotlin releases: https://kotlinlang.org/docs/releases.html#release-details
    application
}

// SLF4J releases: http://www.slf4j.org/news.html
// Note: Jetty is using the 2.x releases of SLF4J. This is cutting edge. I haven't seen other projects jumping to 2.x
// yet. We should use the version of SLF4J that Jetty is using. See https://github.com/eclipse/jetty.project/blob/jetty-11.0.9/pom.xml#L117
val slf4jVersion = "2.0.0-alpha6"

dependencies {
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.slf4j:slf4j-simple:$slf4jVersion")
    implementation("org.http4k:http4k-core:version")
    implementation("org.http4k:http4k-server-jetty")
}

application {
    mainClass.set("dgroomes.JettyMain")
}
