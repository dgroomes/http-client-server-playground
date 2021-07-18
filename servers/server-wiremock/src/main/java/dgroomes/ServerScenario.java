package dgroomes;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

/**
 * Configure a WireMock server as some "scenario"
 */
public interface ServerScenario {

    /**
     * Apply additional configuration
     *
     * @param options WireMockConfiguration
     */
    void configureOptions(WireMockConfiguration options);

    /**
     * Configure the WireMock server via the API
     *
     * @param client WireMock client
     */
    void configureViaApi(WireMock client);
}
