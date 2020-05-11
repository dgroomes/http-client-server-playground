package dgroomes;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.http.AdminRequestHandler;
import com.github.tomakehurst.wiremock.http.HttpServer;
import com.github.tomakehurst.wiremock.http.StubRequestHandler;
import com.github.tomakehurst.wiremock.jetty9.JettyHttpServerFactory;
import com.github.tomakehurst.wiremock.jetty94.Jetty94HttpServer;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.StatisticsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities for working with WireMock
 */
public class WireMockUtil {

    private static final Logger log = LoggerFactory.getLogger(WireMockUtil.class);

    /**
     * Configure the Jetty server with statistics
     * <p>
     * This has a few effects:
     * 1. Enables Jetty statistics, like number of requests, to be available at the URL "/stats/"
     * 2. Enables graceful shutdown
     */
    public static void configureStatistics(WireMockConfiguration options) {
        // A stop timeout and a StatisticsHandler are required to enable Jetty to shutdown gracefully.
        // See the docs at https://www.eclipse.org/jetty/javadoc/9.4.27.v20200227/org/eclipse/jetty/server/Server.html#setStopTimeout(long)
        // See the note at https://github.com/eclipse/jetty.project/issues/2076#issuecomment-353717761
        //
        // But how exactly does it work? Here are some links to dig deeper:
        //   * https://github.com/tomakehurst/wiremock/issues/710
        //   * https://github.com/eclipse/jetty.project/pull/2100/files
        //   * https://github.com/eclipse/jetty.project/pull/2047
        //   * https://github.com/eclipse/jetty.project/issues/2076
        //   * https://github.com/tipsy/javalin/pull/297/files
        //   * https://github.com/tipsy/javalin/issues/286
        options.jettyStopTimeout(10_000L);
        options.httpServerFactory(new JettyHttpServerFactory() {
            @Override
            public HttpServer buildHttpServer(Options options, AdminRequestHandler adminRequestHandler, StubRequestHandler stubRequestHandler) {
                return new Jetty94HttpServer(options, adminRequestHandler, stubRequestHandler) {

                    @Override
                    protected HandlerCollection createHandler(Options options, AdminRequestHandler adminRequestHandler, StubRequestHandler stubRequestHandler) {
                        var handlers = super.createHandler(options, adminRequestHandler, stubRequestHandler);
                        var statisticsHandler = new StatisticsHandler();
                        statisticsHandler.setHandler(handlers);
                        var contexts = new ContextHandlerCollection();
                        ServletContextHandler statsContext = new ServletContextHandler(contexts, "/stats");
                        statsContext.addServlet(new ServletHolder(new StatisticsServlet()), "/");
                        statsContext.setSessionHandler(new SessionHandler());
                        Handler[] existing = handlers.getChildHandlers();
                        Handler[] children = new Handler[existing.length + 1];
                        children[0] = contexts;
                        System.arraycopy(existing, 0, children, 1, existing.length);
                        handlers.setHandlers(children);
                        // Unfortunately, the statisticsHandler needs to be wrapped in a HandlerCollection because the
                        // super method must return the type "HandlerCollection", but really the method can afford to
                        // return the more generic type "Handler". So we must accommodate the unnecessarily specific
                        // type signature.
                        return new HandlerCollection(statisticsHandler);
                    }
                };
            }
        });
    }

    public static void register(WireMock wireMock, MappingBuilder mappingBuilder) {
        var mapping = mappingBuilder.build();
        wireMock.register(mapping);
        log.debug("Registered mapping: {}", mapping);
    }
}
