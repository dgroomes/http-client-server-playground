package dgroomes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Running a simple Spring Boot app to act as a mock HTTP server.");
        var app = new SpringApplication(Main.class);
        app.run(args);
    }
}
