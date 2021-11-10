// This is a "pre-compiled script plugin", or a "convention plugin". See the Gradle docs: https://docs.gradle.org/current/samples/sample_convention_plugins.html#compiling_convention_plugins

plugins {
    java
}

dependencies {
    implementation(enforcedPlatform(project(":dependency-constraints-jmeter")))
}

configurations.all {
    exclude(
        group = "org.apache.jmeter",
        module = "bom"
    ) // Exclude the JMeter "bom" dependency (Bill of Materials) because it doesn't actually exist. See https://bz.apache.org/bugzilla/show_bug.cgi?id=64465 and https://discuss.gradle.org/t/opt-out-of-gradle-module-metadata-for-a-specific-dependency/37051/2
}
