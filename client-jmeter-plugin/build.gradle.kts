plugins {
    /*
    Apply the java-library-distribution plugin to add support for distributing the source .jar and library .jar files
    so they can be used by JMeter. See the plugin docs at https://docs.gradle.org/current/userguide/java_library_distribution_plugin.html
    */
    `java-library-distribution`
}

val jmeterVersion = "5.1.1"

dependencies {
    /*
    Specify the JMeter dependencies as `compileOnly`. These dependencies *do not* need to be included in the
    distribution because they are already included in JMeter itself. So, they are needed only as compile-time
    dependencies. SLF4J is also included in JMeter.
    */
    compileOnly(group = "org.apache.jmeter", name = "ApacheJMeter_java", version = jmeterVersion)
    compileOnly(platform("org.apache.jmeter:ApacheJMeter_parent:$jmeterVersion"))

    implementation(project(":clients:client-httpcomponents-v4"))
}
