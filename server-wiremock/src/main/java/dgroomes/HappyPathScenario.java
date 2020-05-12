package dgroomes;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple happy path scenario. Serves a single stub at the URL "/message" that returns with the body "hello world!"
 */
public class HappyPathScenario implements ServerScenario {

    private static final Logger log = LoggerFactory.getLogger(HappyPathScenario.class);

    @Override
    public void configureOptions(WireMockConfiguration options) {
        log.debug("Configuring WireMockConfiguration for the 'happy path' scenario");
    }

    @Override
    public void configureViaApi(WireMock client) {
        log.debug("Configuring the WireMock server via the API for the 'happy path' scenario");
        var request = WireMock.get("/message");
        var response = ResponseDefinitionBuilder.responseDefinition().withBody("""
                hello world!
                """);
        WireMockUtil.register(client, request.willReturn(response));
    }
}
