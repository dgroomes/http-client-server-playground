plugins {
    // Apply the application plugin to add support for building a CLI application.
    application
}

application {
    // Define the main class for the application.
    mainClassName = "dgroomes.ClientMain"
}

dependencies {
    implementation(project(":client"))
}

tasks {
    named<CreateStartScripts>("startScripts") {
        defaultJvmOpts = listOf("--enable-preview")
    }

    named<JavaExec>("run") {
        jvmArgs = listOf("--enable-preview")
    }
}