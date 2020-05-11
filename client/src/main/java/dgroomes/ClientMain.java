package dgroomes;

import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

public class ClientMain {

    private static final Logger log = LoggerFactory.getLogger(ClientMain.class);

    public static void main(String[] args) throws IOException, URISyntaxException {
        var client = new Client(HttpClients.createDefault(), "http://localhost:8070");

        var message = client.message();
        log.info("Got message: {}", message);
    }
}
