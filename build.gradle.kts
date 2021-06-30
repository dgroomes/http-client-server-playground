plugins {
    // Apply the java plugin to add support for Java
    java
}

extra["slf4jVersion"] = "1.7.30"
extra["wireMockVersion"] = "2.26.3"
extra["junitJupiterVersion"] = "5.6.0"

subprojects {
    // Do we really need to declare the java plugin here *and* at the top of this file?
    apply(plugin = "java")

    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter-api:${project.rootProject.extra["junitJupiterVersion"]}")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${project.rootProject.extra["junitJupiterVersion"]}")
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(16))
        }
    }

    tasks {
        test {
            useJUnitPlatform()
        }
    }
}
