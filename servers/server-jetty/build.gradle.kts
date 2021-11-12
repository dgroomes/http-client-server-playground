plugins {
    id("common")
    kotlin("jvm") version "1.5.31" // Kotlin releases: https://kotlinlang.org/docs/releases.html#release-details
    application
}

// SLF4J releases: http://www.slf4j.org/news.html
// Note: Jetty is using the 2.x releases of SLF4J. This is cutting edge. I haven't seen other projects jumping to 2.x
// yet. We should use the version of SLF4J that Jetty is using. See https://github.com/eclipse/jetty.project/blob/b06ccf286d8b72c745883a8218e065cba455dcf4/pom.xml#L111
val slf4jVersion = "2.0.0-alpha5"

dependencies {
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.slf4j:slf4j-simple:$slf4jVersion")
    implementation("org.http4k:http4k-core:version")
    implementation("org.http4k:http4k-server-jetty")
}

application {
    mainClass.set("dgroomes.JettyMain")
}
