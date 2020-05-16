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
        // Use jcenter for resolving dependencies.
        // You can declare any Maven/Ivy/file repository here.
        jcenter()
    }

    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter-api:${project.rootProject.extra["junitJupiterVersion"]}")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${project.rootProject.extra["junitJupiterVersion"]}")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_14
        targetCompatibility = JavaVersion.VERSION_14
    }

    tasks {
        /**
         * Enable Java language preview features (so we can use "records" and "text blocks")
         */
        withType(JavaCompile::class.java) {
            options.compilerArgs.addAll(arrayOf("--enable-preview"))
        }

        withType(Test::class.java) {
            jvmArgs = listOf("--enable-preview")
            useJUnitPlatform()
        }
    }
}
