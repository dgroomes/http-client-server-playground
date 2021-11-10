import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

plugins {
    id("common")
    id("org.springframework.boot") version "2.5.2" apply false // releases: https://spring.io/projects/spring-boot#learn
    application
}

apply(plugin = "io.spring.dependency-management")

/*
Use Spring Boot dependency management without applying the Spring Boot Gradle plugin.
See https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/html/#managing-dependencies-dependency-management-plugin-using-in-isolation

F.A.Q
Q: Why not use the Spring Boot plugin?
A: Because we are comfortable using the built-in Gradle 'application' plugin.

Q: What is the difference between the 'application' plugin and the Spring Boot plugin?
A: Among other things the Spring Boot plugin creates an executable (a.k.a. "fat) JAR. It also defines a 'bootRun'
   Gradle task that runs the executable JAR. By contrast, the 'application' plugin does not make an executable JAR
   and instead generates a shell "start script" that can be used to run the application.

Q: Should I use an executable JAR or a start scripts?
A: It is mostly a matter of preference. I find that Gradle's incremental build feature works more effectively when
   using the 'application' plugin compared to using the Spring Boot plugin. I think this is because the Spring Boot
   plugin has to make a whole JAR for any change to the project whereas the 'application' plugin does not have to
   pay that cost.
*/
the<DependencyManagementExtension>().apply {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
}

application {
    mainClass.set("dgroomes.Main")
}
