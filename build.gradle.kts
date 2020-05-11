plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application.
    application
}

val slf4jVersion = "1.7.30"
val wireMockVersion = "2.26.3"
val junitJupiterVersion = "5.6.0"

subprojects {
    // Do we really need to declare this java plugin here *and* at the top of this file?
    apply(plugin = "java")
    apply(plugin = "application")

    repositories {
        // Use jcenter for resolving dependencies.
        // You can declare any Maven/Ivy/file repository here.
        jcenter()
    }

    dependencies {
        constraints {
            implementation(group = "org.slf4j", name = "slf4j-api", version = slf4jVersion)
            implementation(group = "org.slf4j", name = "slf4j-simple", version = slf4jVersion)
            implementation(group = "com.github.tomakehurst", name = "wiremock-jre8", version = wireMockVersion)
            implementation(group = "com.github.tomakehurst", name = "wiremock-standalone", version = wireMockVersion)
        }

        testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
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

        named<CreateStartScripts>("startScripts") {
            defaultJvmOpts = listOf("--enable-preview")
        }

        named<JavaExec>("run") {
            jvmArgs = listOf("--enable-preview")
        }
    }
}
