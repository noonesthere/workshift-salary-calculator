package calculator


import calculator.workshift.ExportRoutes
import calculator.workshift.WorkShiftRoutes
import klite.*
import klite.annotations.annotated
import klite.jdbc.HikariModule
import klite.jdbc.RequestTransactionHandler
import klite.json.JsonBody
import klite.liquibase.LiquibaseModule
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.reflect.full.primaryConstructor

val assetsPath = Path.of("ui/dist").takeIf { it.exists() } ?: Path.of("ui/public")

fun startServer() = Server(
  requestIdGenerator = RequestIdGenerator(),
  httpExchangeCreator = XForwardedHttpExchange::class.primaryConstructor!!
).apply {
  use<LiquibaseModule>()
  use<HikariModule>()
  use<JsonBody>()
  use<RequestTransactionHandler>()

  assets("/", AssetsHandler(assetsPath, useIndexForUnknownPaths = true))

  context("/export") {
    annotated<ExportRoutes>()
  }

  context("/api") {
    post("/js-error") { logger("js-error").error(rawBody) }

    useHashCodeAsETag()
    useOnly<JsonBody>()

    annotated<WorkShiftRoutes>("/workshifts")
  }
  if (!Config.isProd) {
    org.h2.tools.Server.createWebServer("-web", "-webPort", "8082").start()
  }
  start()
}

fun main() {
  Config.useEnvFile()
  startServer()
}
