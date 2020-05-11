package dgroomes;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;

/**
 * Run a WireMock server
 * <p>
 * See http://wiremock.org/
 */
public class ServerWireMockMain {

    private static final Logger log = LoggerFactory.getLogger(ServerWireMockMain.class);
    private static final int PORT = 8070;

    public static void main(String[] args) {
        var start = Instant.now();
        var options = new WireMockConfiguration()
                .port(PORT)
                /*
                It's important to disable the request journal when there will be a high volume of traffic or else the
                JVM would run out of memory.
                 */
                .disableRequestJournal();
        WireMockUtil.configureStatistics(options);
        var wireMockServer = new WireMockServer(options);
        log.debug("Starting the WireMock server");
        wireMockServer.start();

        var wireMock = new WireMock(PORT);
        ServerScenarios.happyPath(wireMock);
        var startDuration = Duration.between(start, Instant.now());
        log.info("WireMock server started!");
        log.debug("Boot up time was {}", startDuration);
    }
}
