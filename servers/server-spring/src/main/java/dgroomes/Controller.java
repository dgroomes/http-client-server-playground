package dgroomes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    public static final int FIXED_DELAY_MILLISECONDS = 100;

    @GetMapping(path = "message")
    public String message() throws InterruptedException {
        Thread.sleep(FIXED_DELAY_MILLISECONDS);
        return "hello world!";
    }
}
