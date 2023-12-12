package dgroomes.micronaut;


import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/message")
public class MessageController {

    public static final int FIXED_DELAY_MILLISECONDS = 100;

    @Get(produces = {MediaType.TEXT_PLAIN, MediaType.ALL})
    public String message() throws InterruptedException {
        Thread.sleep(FIXED_DELAY_MILLISECONDS);
        return "hello world!";
    }
}
