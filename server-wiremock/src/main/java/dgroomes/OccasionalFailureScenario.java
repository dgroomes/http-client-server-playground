package dgroomes;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simulate occasional failure. Every third request to the "/message" endpoint will terminate in error.
 */
public class OccasionalFailureScenario implements ServerScenario {

    private static final Logger log = LoggerFactory.getLogger(OccasionalFailureScenario.class);

    @Override
    public void configureOptions(WireMockConfiguration options) {
        log.debug("Configuring WireMockConfiguration for the 'occasional failure' scenario");
        WireMockUtil.configureRootDir(options, "wiremock/scenarios/occasional-failure");
        WireMockUtil.configureStatistics(options);
        /*
        It's important to disable the request journal when there will be a high volume of traffic or else the
        JVM would run out of memory.
        */
        options.disableRequestJournal();
    }

    public void configureViaApi(WireMock client) {
        log.debug("Configuring the WireMock server via the API for the 'occasional failure' scenario");
    }
}
