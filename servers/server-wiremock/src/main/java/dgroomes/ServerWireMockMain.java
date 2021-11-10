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
        var options = new WireMockConfiguration()
                .port(PORT)
                /*
                 With GZIP compression enabled, the Apache HTTP Component's "wire" logging prints the gzipped contents
                 of the HTTP request/response bodies. This makes observability harder and a "playground" repo is about learning.
                 So, disable GZIP compression.
                 */
                .gzipDisabled(true);
                // For some reason, async doesn't work when the statistics handler is enabled. So, this must stay commented
                // out. I'd really like to figure out why this happens.
                //.asynchronousResponseEnabled(true)
                //.asynchronousResponseThreads(1000);

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
        return new HappyPathScenario();
//        return new OccasionalFailureScenario();
    }
}
