package dgroomes;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * HTTP client backed by Apache HTTP Components <https://hc.apache.org/index.html>
 */
public class ClientV4 implements Client {

    private static final Logger log = LoggerFactory.getLogger(ClientV4.class);

    private final CloseableHttpClient httpClient;
    private final String serverOrigin;

    public ClientV4(String serverOrigin, boolean pooling) {
        var builder = HttpClients.custom();
        if (pooling) {
            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
            cm.setMaxTotal(400);
            cm.setDefaultMaxPerRoute(100);
            builder.setConnectionManager(cm);
        } else {
            // Disable connection re-use. It seems like the connections are being terminated from the WireMock server (Jetty)
            // and I'm not sure why. Also, I used this SO question and answers to learn about connection re-use and workarounds
            // https://stackoverflow.com/questions/10558791/apache-httpclient-interim-error-nohttpresponseexception
            builder.setConnectionReuseStrategy(new NoConnectionReuseStrategy());
        }

        this.httpClient = builder.build();
        this.serverOrigin = serverOrigin;
    }

    @Override
    public String message() throws IOException {
        HttpGet httpGet = new HttpGet(serverOrigin + "/message");
        /*
        Quoting the Quick Start guide https://hc.apache.org/httpcomponents-client-4.5.x/quickstart.html:
            The underlying HTTP connection is still held by the response object
            to allow the response content to be streamed directly from the network socket.
            In order to ensure correct deallocation of system resources
            the user MUST call CloseableHttpResponse#close() from a finally clause.
            Please note that if response content is not fully consumed the underlying
            connection cannot be safely re-used and will be shut down and discarded
            by the connection manager.

        Using a try-with-resources block which will automatically call "#close()".
        */
        log.debug("Executing request to '/message'");
        try (CloseableHttpResponse resp = httpClient.execute(httpGet)) {
            return handleResponse(resp);
        }
    }

    @Override
    public void messageNoClose() throws IOException {
        HttpGet httpGet = new HttpGet(serverOrigin + "/message");
        log.debug("Executing request to '/message' but NOT closing the HTTP response");
        var resp = httpClient.execute(httpGet);
        handleResponse(resp);
    }

    private static String handleResponse(CloseableHttpResponse resp) throws IOException {
        log.debug("GET request to '/message' returned with status code: {}", resp.getStatusLine());
        HttpEntity entity = resp.getEntity();
        // Extract the body of the response. EntityUtils ensures the response entity is fully consumed.
        return EntityUtils.toString(entity);
    }
}
