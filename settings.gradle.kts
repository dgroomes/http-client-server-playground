rootProject.name = "http-client-server-playground"

include(
    "dependency-constraints",
    "dependency-constraints-jmeter",
    "clients:client-api",
    "clients:client-httpcomponents-v4",
    "clients:client-httpcomponents-v5",
    "client-runner",
    "client-jmeter-plugin",
    "servers:server-wiremock",
    "servers:server-spring",
    "servers:server-netty",
)
