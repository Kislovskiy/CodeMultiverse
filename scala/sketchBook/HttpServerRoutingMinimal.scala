//> using scala 3
//> using dep "org.apache.pekko::pekko-actor-typed:1.1.4"
//> using dep "org.apache.pekko::pekko-stream::1.1.4"
//> using dep "org.apache.pekko::pekko-http::1.2.0"

import org.apache.pekko.actor.typed.ActorSystem
import org.apache.pekko.actor.typed.scaladsl.Behaviors
import org.apache.pekko.http.scaladsl.Http
import org.apache.pekko.http.scaladsl.model.*
import org.apache.pekko.http.scaladsl.server.Directives.*

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object HttpServerRoutingMinimal {
  // rate limit: https://gist.github.com/johanandren/b87a9ed63b4c3e95432dc0497fd73fdb
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem[Any] =
      ActorSystem(Behaviors.empty, "my-system")
    implicit val executionContext: ExecutionContextExecutor =
      system.executionContext

    val route =
      path("hello") {
        get {
          complete(
            HttpEntity(
              ContentTypes.`text/html(UTF-8)`,
              "<h1>Say hello to pekko-http</h1>"
            )
          )
        }
      }

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)

    println(
      s"Server now online. Use: curl http://localhost:8080/hello\nPress RETURN to stop..."
    )
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}
