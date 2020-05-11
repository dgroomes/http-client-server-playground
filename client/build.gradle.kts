application {
    // Define the main class for the application.
    mainClassName = "dgroomes.ClientMain"
}

val httpComponentsVersion = "4.5.12"

dependencies {
    implementation("org.apache.httpcomponents:httpclient:$httpComponentsVersion")
}