plugins {
    id("common")
    alias(libs.plugins.kotlin.jvm)
    application
}

dependencies {
    implementation("org.slf4j:slf4j-api")
    implementation("org.slf4j:slf4j-simple")
    implementation("org.http4k:http4k-server-jetty")
}

application {
    mainClass.set("dgroomes.JettyMain")
}
