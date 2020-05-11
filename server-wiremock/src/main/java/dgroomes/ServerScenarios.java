package dgroomes;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Serving HTTP requests in various scenarios
 */
public class ServerScenarios {

    private static final Logger log = LoggerFactory.getLogger(ServerScenarios.class);

    /**
     * Register a 'happy path' scenario
     *
     * @param wireMock the WireMock client
     */
    public static void happyPath(WireMock wireMock) {
        log.debug("Registering 'happy path' scenario");
        var request = WireMock.get("/message");
        var response = ResponseDefinitionBuilder.responseDefinition().withBody("""
                hello world!
                """);
        WireMockUtil.register(wireMock, request.willReturn(response));
    }
}
