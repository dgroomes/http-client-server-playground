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

    @Override
    public SampleResult runTest(JavaSamplerContext context) {
        log.info("Running the SerializerSampler test!");
        var sampleResult = new SampleResult();
        Client client;
        try {
            client = new Client(SERVER_ORIGIN);
        } catch (IOException | URISyntaxException e) {
            log.error("Error initializing client", e);
            sampleResult.setSuccessful(false);
            sampleResult.sampleEnd();
            return sampleResult;
        }

        sampleResult.sampleStart();
        try {
            client.message();
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
