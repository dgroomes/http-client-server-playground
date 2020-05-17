package dgroomes;

import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Custom JMeter sample class to test the {@link dgroomes.Client} class.
 * <p>
 * For a reference example of a custom JMeter sample implementation, see https://github.com/apache/jmeter/blob/8fd448fe366b4e193c1e9a2d24eb974225135eec/src/protocol/java/src/main/java/org/apache/jmeter/protocol/java/test/SleepTest.java#L50
 */
public class ClientSampler extends AbstractJavaSamplerClient {

    private static final Logger log = LoggerFactory.getLogger(ClientSampler.class);
    public static final String SERVER_ORIGIN = "http://localhost:8070";
    public static volatile Client CLIENT;

    /**
     * My perhaps naive attempt at initializing a static variable using double checked locking.
     * <p>
     * Why do we need this? JMeter will create multiple instances of ClientSampler based on the "Thread Groups"
     * configuration in the test plan. But, we don't want multiple instances of dgroomes.Client. So, we need to
     * coordinate the initialization of Client to guruantee exactly one is instantiated.
     *
     * https://en.wikipedia.org/wiki/Double-checked_locking
     *
     * @param serverOrigin the origin of the HTTP server this sampler will make requests to
     */
    public static void initClient(String serverOrigin) throws IOException, URISyntaxException {
        var localRef = CLIENT;
        if (localRef == null) {
            synchronized (ClientSampler.class) {
                localRef = CLIENT;
                if (localRef == null) {
                    CLIENT = new Client(serverOrigin);
                }
            }
        }
    }

    @Override
    public void setupTest(JavaSamplerContext context) {
        super.setupTest(context);
        try {
            initClient(SERVER_ORIGIN);
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public SampleResult runTest(JavaSamplerContext context) {
        log.info("Running the SerializerSampler test!");
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
