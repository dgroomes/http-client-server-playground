package dgroomes;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;

/**
 * Run a WireMock server under some "scenario"
 * <p>
 * See http://wiremock.org/
 */
public class ServerWireMockMain {

    private static final Logger log = LoggerFactory.getLogger(ServerWireMockMain.class);
    private static final int PORT = 8070;

    public static void main(String[] args) {
        var start = Instant.now();
        var options = new WireMockConfiguration().port(PORT);

        var scenario = getScenario();
        scenario.configureOptions(options);

        var wireMockServer = new WireMockServer(options);
        log.debug("Starting the WireMock server");
        wireMockServer.start();

        var client = new WireMock(PORT);
        scenario.configureViaApi(client);

        var startDuration = Duration.between(start, Instant.now());
        log.info("WireMock server started!");
        log.debug("Boot up time was {}", startDuration);
        log.debug("View statistics at http://localhost:{}/stats", PORT);
    }

    private static ServerScenario getScenario() {
//        return new HappyPathScenario();
        return new OccasionalFailureScenario();
    }
}
