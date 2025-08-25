//> using dep "org.apache.pekko::pekko-actor-typed:1.1.5"
//> using dep "org.apache.pekko::pekko-stream::1.1.5"
//> using dep "org.apache.pekko::pekko-stream-testkit::1.1.5"
//> using dep "org.scalatest::scalatest::3.2.19"

import org.apache.pekko.actor.typed.ActorSystem
import org.apache.pekko.actor.typed.scaladsl.Behaviors
import org.apache.pekko.stream.scaladsl.{Flow, Sink, Source}
import org.apache.pekko.stream.testkit.scaladsl.TestSink
import org.apache.pekko.{Done, NotUsed}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

def sourceUnderTest: Source[Int, NotUsed] =
  Source(1 to 4)
    .filter(_ % 2 == 0)
    .map(_ * 2)

def slowSink: Sink[Int, Future[Done]] = Sink.foreach[Int] { x =>
  Thread.sleep(1000)
  println(s"🐌 Slow sink processed: $x")
}

@main def pekkoStreams(): Unit =
  println("run 'scala-cli test pekkoStreams.scala' to execute the tests")

class PekkoStreamsBackpressureTest
    extends AnyFlatSpec
    with Matchers
    with BeforeAndAfterAll {
  implicit val system: ActorSystem[Nothing] =
    ActorSystem(Behaviors.empty, "BackpressureBasics")

  override def afterAll(): Unit = {
    println("🧹 Cleaning up ActorSystem...")
    system.terminate()
    println("✅ ActorSystem terminated successfully")
  }

  "A simple source transformation" should "produce the expected elements synchronously" in {
    val testSink = sourceUnderTest.runWith(TestSink[Int]())
    testSink
      .request(1)
      .expectNext(4)
      .request(1)
      .expectNext(8)
      .expectComplete()
  }

  it should "demonstrate backpressure with a slow sink" in {
    val startTime = System.currentTimeMillis()
    val future = sourceUnderTest.runWith(slowSink)
    Await.result(future, 10.seconds)
    val elapsed = System.currentTimeMillis() - startTime
    println(s"⏱️  Stream completed in $elapsed ms")
    elapsed should be >= 2000L
  }

  it should "demonstrate filtering of names" in {
    val names = List("Alice", "Bob", "Charlie", "David", "Martin", "AkkaStreams")
    val nameSource = Source(names)
    val longNameFlow = Flow[String].filter(name => name.length > 5)
    val limitFlow = Flow[String].take(2)

    val testSink = nameSource.via(longNameFlow).via(limitFlow).runWith(TestSink[String]())
    testSink
      .request(1)
      .expectNext("Charlie")
      .request(1)
      .expectNext("Martin")
      .expectComplete()

    val testSink2 = nameSource.via(longNameFlow).via(limitFlow).runWith(TestSink[String]())
    testSink2
      .request(10)
      .expectNext("Charlie")
      .expectNext("Martin")
      .expectComplete()
  }
}
