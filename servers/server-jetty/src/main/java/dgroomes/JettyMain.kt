package dgroomes

import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.slf4j.LoggerFactory

object JettyMain {
    private val log = LoggerFactory.getLogger(JettyMain::class.java)
    private const val FIXED_DELAY_MILLISECONDS = 100L
    private const val PORT = 8070

    @JvmStatic
    fun main(args: Array<String>) {
        log.info("Running a simple Jetty http4k program to act as a mock HTTP server.")

        val handler: HttpHandler = {
            Thread.sleep(FIXED_DELAY_MILLISECONDS);
            if (it.uri.path.contains("message")) {
                Response(OK).body("hello world!")
            } else {
                Response(NOT_FOUND)
            }
        }

        val server = handler.asServer(Jetty(PORT))
        server.start()
    }
}
