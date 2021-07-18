rootProject.name = "httpcomponents-playground"

include("dependency-constraints",
        "clients:client-api",
        "clients:client-httpcomponents-v4",
        "clients:client-httpcomponents-v5",
        "client-runner",
        "client-jmeter-plugin",
        "server-wiremock")
