package dgroomes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;

/**
 * Executing HTTP requests in various scenarios
 */
public class ClientScenarios {

    private static final Logger log = LoggerFactory.getLogger(ClientScenarios.class);

    /**
     * Execute a single request
     */
    public static void singleRequest(Client client) throws IOException {
        var message = client.message();
        log.info("Got message: {}", message);
    }

    /**
     * Execute requests continuously
     *
     * @param client     client
     * @param fixedDelay fixed delay between requests
     */
    public static void continuousRequests(Client client, Duration fixedDelay) throws IOException, InterruptedException {
        log.info("Continuously making requests to '/message' with a fixed delay of {}", fixedDelay);
        int counter = 0;
        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                client.message();
                counter++;
                //noinspection BusyWait
                Thread.sleep(fixedDelay.toMillis());
            }
        } catch (IOException | InterruptedException e) {
            log.error("Exception during 'continuousRequests' scenario", e);
        } finally {
            log.info("Executed {} requests", counter);
        }
    }
}
