plugins {
    id("common")
    // Apply the application plugin to add support for building a CLI application.
    application
}

dependencies {
    implementation("com.github.tomakehurst:wiremock-jre8")
    implementation("org.slf4j:slf4j-api")
    implementation("org.slf4j:slf4j-simple")
}

application {
    mainClass.set("dgroomes.ServerWireMockMain")
}
