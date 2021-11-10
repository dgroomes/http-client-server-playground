plugins {
    id("common")
    id("jmeter-constraints")
    // Apply the application plugin to add support for building a CLI application.
    application
}

application {
    // Define the main class for the application.
    mainClass.set("dgroomes.ClientMain")
}

dependencies {
    implementation(project(":clients:client-httpcomponents-v4"))
    implementation(project(":clients:client-httpcomponents-v5"))
    implementation("org.slf4j:slf4j-api")
}
