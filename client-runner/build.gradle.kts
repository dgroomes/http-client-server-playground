plugins {
    // Apply the application plugin to add support for building a CLI application.
    application
}

application {
    // Define the main class for the application.
    mainClass.set("dgroomes.ClientMain")
}

dependencies {
    implementation(project(":client"))
}
