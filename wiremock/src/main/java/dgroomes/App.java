package dgroomes;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Run a WireMock server
 *
 * See http://wiremock.org/
 */
public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);
    public static final int SLEEP_SECONDS = 1;

    public static void main(String[] args) throws InterruptedException {
        WireMockConfiguration options = WireMockUtil.shutdownGracefully(new WireMockConfiguration());
        WireMockServer wireMockServer = new WireMockServer(options);
        log.info("Starting the WireMock server");
        wireMockServer.start();
    }

}
