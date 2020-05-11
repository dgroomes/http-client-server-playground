package dgroomes;

import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;

public class ClientMain {

    private static final Logger log = LoggerFactory.getLogger(ClientMain.class);
    private static final int FIXED_DELAY_MILLIS = 500;

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        var client = new Client(HttpClients.createDefault(), "http://localhost:8070");

//        ClientScenarios.singleRequest(client);
//        ClientScenarios.multipleRequests(client, 3);
        ClientScenarios.continuousRequests(client, Duration.ofMillis(FIXED_DELAY_MILLIS), true);
//        ClientScenarios.continuousRequests(client, Duration.ofMillis(FIXED_DELAY_MILLIS), false);
    }
}
