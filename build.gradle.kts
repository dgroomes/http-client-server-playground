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

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(16))
        }
    }
}
