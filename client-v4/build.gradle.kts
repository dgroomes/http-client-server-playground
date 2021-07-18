plugins {
    /*
    Apply the java-library-distribution plugin to add support for distributing the source .jar and library .jar files
    so they can be used by JMeter. See the plugin docs at https://docs.gradle.org/current/userguide/java_library_distribution_plugin.html
    */
    `java-library-distribution`
}

val httpComponentsVersion = "4.5.12"

dependencies {
    api(project(":client-api"))
    implementation("org.apache.httpcomponents:httpclient:$httpComponentsVersion") {
        // Exclude commons-logging because we are using slf4j and slf4j-simple instead
        exclude("commons-logging", "commons-logging")
    }
    implementation("org.slf4j:slf4j-simple")
    implementation("org.slf4j:jcl-over-slf4j")
}
