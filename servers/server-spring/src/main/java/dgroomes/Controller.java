package dgroomes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping(path = "message")
    public String message() {
        return "hello world!";
    }
}
