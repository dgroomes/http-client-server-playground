package dgroomes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;

/**
 * Run some scenario using one of the HTTP clients.
 * <p>
 * For example, "execute multiple requests using the Apache HttpComponents v4.x client library".
 */
public class ClientMain {

    private static final Logger log = LoggerFactory.getLogger(ClientMain.class);
    private static final int FIXED_DELAY_MILLIS = 500;
    private static final String SERVER_ORIGIN = "http://localhost:8070";

    public static void main(String[] args) {

        var main = new ClientMain();
        try {
            main.run(args);
        } catch (Exception e) {
            log.error("Something went wrong while running the client scenario", e);
        }
    }

    public void run(String[] args) throws IOException {
        if (args.length != 2) {
            log.error("Usage: ./gradlew run <client> <scenario>");
            return;
        }

        var clientArg = args[0].toLowerCase();
        var scenarioArg = args[1].toLowerCase();

        Client client = switch (clientArg) {
            case "httpcomponents-v4" -> new ClientV4(SERVER_ORIGIN);
            case "httpcomponents-v5" -> new ClientV5(SERVER_ORIGIN);
            default -> {
                log.error("Unrecognized <client> option: '{}'. See the source code for valid options.", clientArg);
                yield null;
            }
        };
        if (client == null) {
            return;
        }

        switch (scenarioArg) {
            case "single-request" -> ClientScenarios.singleRequest(client);
            case "multiple-requests" -> ClientScenarios.multipleRequests(client, 3);
            case "continuous-requests-with-close" -> ClientScenarios.continuousRequests(client, Duration.ofMillis(FIXED_DELAY_MILLIS), true);
            case "continuous-requests-without-close" -> ClientScenarios.continuousRequests(client, Duration.ofMillis(FIXED_DELAY_MILLIS), false);
            default -> log.error("Unrecognized <scenario> option: '{}'. See the source code for valid options.", scenarioArg);
        }
    }
}
