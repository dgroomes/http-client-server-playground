plugins {
    id("common")
    id("jmeter-constraints")
    `java-library-distribution`
}

dependencies {
    api(project(":clients:client-api"))
    implementation("org.apache.httpcomponents.client5:httpclient5") {
        // Exclude commons-logging because we are using slf4j and slf4j-simple instead
        exclude("commons-logging", "commons-logging")
    }
    implementation("org.slf4j:slf4j-simple")
    implementation("org.slf4j:jcl-over-slf4j")
}
