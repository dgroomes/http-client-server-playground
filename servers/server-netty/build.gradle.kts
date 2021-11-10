plugins {
    id("common")
    kotlin("jvm") version "1.5.31" // Kotlin releases: https://kotlinlang.org/docs/releases.html#release-details
    application
}

dependencies {
    implementation("org.slf4j:slf4j-api")
    implementation("org.slf4j:slf4j-simple")
    implementation("org.http4k:http4k-core:version")
    implementation("org.http4k:http4k-server-netty")
}

application {
    mainClass.set("dgroomes.NettyMain")
}