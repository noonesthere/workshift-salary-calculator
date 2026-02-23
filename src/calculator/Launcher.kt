package calculator

import calculator.workshift.WorkShiftRoutes
import klite.*
import klite.annotations.annotated
import klite.jdbc.RequestTransactionHandler
import klite.json.JsonBody
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.reflect.full.primaryConstructor


val assetsPath = Path.of("ui/dist").takeIf { it.exists() } ?: Path.of("ui/public")

fun startServer() = Server(
  httpExchangeCreator = XForwardedHttpExchange::class.primaryConstructor!!
).apply {

  use<JsonBody>()
  use<RequestTransactionHandler>()

  assets("/", AssetsHandler(assetsPath, useIndexForUnknownPaths = true))


  context("/api") {
    post("/js-error") { logger("js-error").error(rawBody) }

    useHashCodeAsETag()
    useOnly<JsonBody>()

    annotated<WorkShiftRoutes>("/workshifts")
  }
  start()
}


fun main() {
  Config.useEnvFile()
  startServer()
}
