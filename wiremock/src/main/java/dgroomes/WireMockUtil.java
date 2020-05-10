package dgroomes;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities for working with WireMock
 */
public class WireMockUtil {

    private static final Logger log = LoggerFactory.getLogger(WireMockUtil.class);

    public static void register(WireMock wireMock, MappingBuilder mappingBuilder) {
        var mapping = mappingBuilder.build();
        wireMock.register(mapping);
        log.debug("Registered mapping: {}", mapping);
    }
}
