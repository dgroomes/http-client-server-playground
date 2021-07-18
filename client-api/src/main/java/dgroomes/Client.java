package dgroomes;

import java.io.IOException;

public interface Client {

    /**
     * Execute an HTTP GET request to the server at the "/message" path
     *
     * @param closeResponse if true, then close the HTTP response
     */
    default void message(boolean closeResponse) throws IOException {
        if (closeResponse) {
            message();
        } else {
            messageNoClose();
        }
    }

    /**
     * Execute an HTTP GET request to the server at the "/message" path
     *
     * @return the "message". The body of the HTTP response.
     */
    String message() throws IOException;

    /**
     * Execute an HTTP GET request to the server at the "/message" path but DO NOT close the HTTP response
     *
     */
    void messageNoClose() throws IOException;
}
