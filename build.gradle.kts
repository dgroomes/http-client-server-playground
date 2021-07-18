plugins {
    // Apply the java plugin to add support for Java
    java
}

val dependencyConstraints = project(":dependency-constraints")
configure(allprojects.minus(dependencyConstraints)) {
    // Do we really need to declare the java plugin here *and* at the top of this file?
    apply(plugin = "java")

    repositories {
        mavenCentral()
    }

    dependencies {
        "implementation"(platform(dependencyConstraints))
    }

    configurations.all {
        exclude(
            group = "org.apache.jmeter",
            module = "bom"
        ) // Exclude the JMeter "bom" dependency (Bill of Materials) because it doesn't actually exist. See https://bz.apache.org/bugzilla/show_bug.cgi?id=64465 and https://discuss.gradle.org/t/opt-out-of-gradle-module-metadata-for-a-specific-dependency/37051/2
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(16))
        }
    }
}
