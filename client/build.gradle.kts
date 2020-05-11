application {
    // Define the main class for the application.
    mainClassName = "dgroomes.ClientMain"
}

val httpComponentsVersion = "4.5.12"

dependencies {
    implementation("org.apache.httpcomponents:httpclient:$httpComponentsVersion") {
        // Exclude commons-logging because we are using slf4j and slf4j-simple instead
        exclude("commons-logging", "commons-logging")
    }
    implementation("org.slf4j", "jcl-over-slf4j")
}