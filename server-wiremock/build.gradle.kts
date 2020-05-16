plugins {
    // Apply the application plugin to add support for building a CLI application.
    application
}

dependencies {
    implementation("com.github.tomakehurst:wiremock-jre8:${project.rootProject.extra["wireMockVersion"]}")
    implementation("org.slf4j:slf4j-api:${project.rootProject.extra["slf4jVersion"]}")
    implementation("org.slf4j:slf4j-simple:${project.rootProject.extra["slf4jVersion"]}")
}

application {
    mainClassName = "dgroomes.ServerWireMockMain"
}

tasks {
    named<CreateStartScripts>("startScripts") {
        defaultJvmOpts = listOf("--enable-preview")
    }

    named<JavaExec>("run") {
        jvmArgs = listOf("--enable-preview")
    }
}
