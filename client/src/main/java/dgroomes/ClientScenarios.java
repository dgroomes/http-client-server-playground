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
        log.info("Executing a single request");
        var message = client.message();
        log.info("Got message: {}", message);
    }

    /**
     * Execute multiple requests
     *
     * @param client           client
     * @param numberOfRequests the number of requests
     */
    public static void multipleRequests(Client client, int numberOfRequests) throws IOException {
        log.info("Executing {} requests", numberOfRequests);
        for (int i = 1; i <= numberOfRequests; i++) {
            client.message();
        }
    }

    /**
     * Execute requests continuously
     *
     * @param client     client
     * @param fixedDelay fixed delay between requests
     * @param closeResponse if true, then close the response object
     */
    public static void continuousRequests(Client client, Duration fixedDelay, boolean closeResponse) throws IOException, InterruptedException {
        log.info("Continuously making requests to '/message' with a fixed delay of {} and with closeResponse: {}",
                fixedDelay, closeResponse);
        int counter = 0;
        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                client.message(closeResponse);
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
