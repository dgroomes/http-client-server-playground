package dgroomes;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

/**
 * Custom JMeter sample class to test one of the concrete implementations of the {@link Client} interface.
 * <p>
 * For a reference example of a custom JMeter sample implementation, see https://github.com/apache/jmeter/blob/8fd448fe366b4e193c1e9a2d24eb974225135eec/src/protocol/java/src/main/java/org/apache/jmeter/protocol/java/test/SleepTest.java#L50
 */
public class ClientSampler extends AbstractJavaSamplerClient {

    private static final Logger log = LoggerFactory.getLogger(ClientSampler.class);
    private static final String SERVER_ORIGIN = "http://localhost:8070";
    private static final HttpClientLibrary DEFAULT_HTTP_CLIENT_LIBRARY = HttpClientLibrary.HTTPCOMPONENTS_V4;
    public static volatile Client CLIENT;

    /**
     * My perhaps naive attempt at initializing a static variable using double checked locking.
     * <p>
     * Why do we need this? JMeter will create multiple instances of ClientSampler based on the "Thread Groups"
     * configuration in the test plan. But, we don't want multiple instances of the client object. So, we need to
     * coordinate the initialization of the client to guarantee exactly one is instantiated.
     * <p>
     * https://en.wikipedia.org/wiki/Double-checked_locking
     *
     * @param httpClientLibrary the HTTP client library to use
     */
    public static void initClient(boolean pooling, HttpClientLibrary httpClientLibrary) throws IOException, URISyntaxException {
        var localRef = CLIENT;
        if (localRef == null) {
            synchronized (ClientSampler.class) {
                localRef = CLIENT;
                if (localRef == null) {
                    CLIENT = switch (httpClientLibrary) {
                        case HTTPCOMPONENTS_V4 -> new ClientV4(SERVER_ORIGIN, pooling);
                        case HTTPCOMPONENTS_V5 -> new ClientV5(SERVER_ORIGIN, pooling);
                    };
                }
            }
        }
    }

    enum HttpClientLibrary {
        HTTPCOMPONENTS_V4, HTTPCOMPONENTS_V5;
    }

    @Override
    public Arguments getDefaultParameters() {
        Arguments params = new Arguments();
        params.addArgument("Pooling", "false");
        params.addArgument("HttpClientLibrary", "httpcomponents-v4");
        return params;
    }

    @Override
    public void setupTest(JavaSamplerContext context) {
        super.setupTest(context);
        try {
            boolean pooling;
            var poolingParam = context.getParameter("Pooling");
            if (poolingParam == null) {
                pooling = false;
            } else {
                log.info("Detected 'Pooling' param with value '{}'", poolingParam);
                pooling = Boolean.parseBoolean(poolingParam);
            }

            HttpClientLibrary httpClientLibrary;
            var httpClientLibraryParam = context.getParameter("HttpClientLibrary");
            if (httpClientLibraryParam == null) {
                httpClientLibrary = DEFAULT_HTTP_CLIENT_LIBRARY;
            } else {
                log.info("Detected 'HttpClientLibrary' param with value '{}'", httpClientLibraryParam);
                var found = Arrays.stream(HttpClientLibrary.values())
                        .filter(value -> value.name().replace("_", "-").equalsIgnoreCase(httpClientLibraryParam))
                        .findFirst();
                if (found.isEmpty()) {
                    log.error("The given HttpClientLibrary param did not match any expected values. Using a default instead.");
                    httpClientLibrary = DEFAULT_HTTP_CLIENT_LIBRARY;
                } else {
                    httpClientLibrary = found.get();
                }
            }

            initClient(pooling, httpClientLibrary);
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public SampleResult runTest(JavaSamplerContext context) {
        var sampleResult = new SampleResult();
        sampleResult.sampleStart();
        try {
            CLIENT.message();
        } catch (IOException e) {
            log.error("Error get message", e);
            sampleResult.setSuccessful(false);
            sampleResult.sampleEnd();
            return sampleResult;
        }

        sampleResult.setSuccessful(true);
        sampleResult.sampleEnd();
        return sampleResult;
    }
}
