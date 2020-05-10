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
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static final int PORT = 8070;

    public static void main(String[] args) throws InterruptedException {
        var start = Instant.now();
        var options = new WireMockConfiguration().port(PORT);
        var wireMockServer = new WireMockServer(options);
        log.debug("Starting the WireMock server");
        wireMockServer.start();

        var wireMock = new WireMock(PORT);
        Scenarios.happyPath(wireMock);
        var startDuration = Duration.between(start, Instant.now());
        log.info("WireMock server started!");
        log.debug("Boot up time was {}", startDuration);
    }
}
