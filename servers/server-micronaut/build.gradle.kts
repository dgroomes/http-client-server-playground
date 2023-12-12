plugins {
    id("common")
    application
    // Micronaut Gradle plugin releases: https://github.com/micronaut-projects/micronaut-gradle-plugin/releases
    id("io.micronaut.application") version "4.2.1"
}

micronaut {
    version("4.2.1") // Micronaut releases: https://github.com/micronaut-projects/micronaut-core/releases
    processing {
        incremental(true)
        annotations("dgroomes.micronaut.*")
    }
}

dependencies {
    implementation("org.slf4j:slf4j-api")
    runtimeOnly("org.slf4j:slf4j-simple")
    runtimeOnly("org.yaml:snakeyaml")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("io.micronaut:micronaut-jackson-databind")
}

application {
    mainClass.set("dgroomes.micronaut.MicronautMain")
}
