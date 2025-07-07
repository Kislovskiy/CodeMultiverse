//> using scala 3
//> using dep "org.typelevel::cats-effect::3.6.1"
//> using dep "co.fs2::fs2-core::3.12.0"

import cats.effect.{IO, Ref}
import cats.effect.unsafe.implicits.global

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.FiniteDuration

object fpHelloRef {

  /** Measures execution time of a given function and returns both the result
    * and timing
    *
    * @param f
    *   the function to execute
    * @return
    *   tuple containing (result, execution time in nanoseconds)
    */
  private def timeExecution[T](f: => T): (T, Long) = {
    val startTime = System.nanoTime()
    val result = f
    val endTime = System.nanoTime()
    val executionTime = endTime - startTime
    (result, executionTime)
  }

  /** Measures execution time and prints it in a readable format
    * @param f
    *   the function to execute
    * @param description
    *   optional description of what's being timed
    * @return
    *   the result of the function
    */
  private def timeAndPrint[T](f: => T, description: String = "Operation"): T = {
    val (result, nanos) = timeExecution(f)
    val millis = nanos / 1_000_000.0
    print(f"$description took $millis%.2f ms. ")
    result
  }

  /** This is an example that shows API for Ref. Ref[IO, A] is an immutable
    * value that represents an asynchronous concurrent mutable reference to an
    * immutable value of type A. Ref's embrace compare-and-swap mechanism.
    */
  @main def fpHelloRefRun(): Unit = {
    val refExample: IO[Int] = for {
      counter <- Ref.of[IO, Int](0)
      _ <- counter.update(_ + 3)
      result <- counter.get
    } yield result

    println(s"Ref Example: ${timeAndPrint(refExample.unsafeRunSync())}")

    val exampleSequential: IO[Int] = for {
      counter <- Ref.of[IO, Int](0)
      _ <- List(
        counter.update(_ + 2),
        counter.update(_ + 3),
        counter.update(_ + 4)
      ).sequence
      result <- counter.get
    } yield result

    println(
      s"Example Sequential: ${timeAndPrint(exampleSequential.unsafeRunSync())}"
    )

    val exampleSequentialWithSleep: IO[Int] = for {
      counter <- Ref.of[IO, Int](0)
      program1 = counter.update(_ + 2)
      program2 = IO
        .sleep(FiniteDuration(1, TimeUnit.SECONDS))
        .flatMap(_ => counter.update(_ + 3))
      program3 = IO
        .sleep(FiniteDuration(1, TimeUnit.SECONDS))
        .flatMap(_ => counter.update(_ + 4))
      _ <- List(program1, program2, program3).sequence
      result <- counter.get
    } yield result

    println(
      s"Example Sequential With Sleep: ${timeAndPrint(exampleSequentialWithSleep.unsafeRunSync())}"
    )

    // In FP a multithreaded program can be modeled as an immutable list of sequential programs.

    // Unlike sequence, parSequence doesn't run the IOs sequentially; it runs them in parallel, each in its own
    // "thread"
    val exampleConcurrent: IO[Int] = for {
      counter <- Ref.of[IO, Int](0)
      _ <- List(
        counter.update(_ + 2),
        counter.update(_ + 3),
        counter.update(_ + 4)
      ).parSequence
      result <- counter.get
    } yield result

    println(
      s"Example Concurrent: ${timeAndPrint(exampleConcurrent.unsafeRunSync())}"
    )

    val exampleConcurrentWithSleep: IO[Int] = for {
      counter <- Ref.of[IO, Int](0)
      program1 = counter.update(_ + 2)
      program2 = IO
        .sleep(FiniteDuration(1, TimeUnit.SECONDS))
        .flatMap(_ => counter.update(_ + 3))
      program3 = IO
        .sleep(FiniteDuration(1, TimeUnit.SECONDS))
        .flatMap(_ => counter.update(_ + 4))
      _ <- List(program1, program2, program3).parSequence
      result <- counter.get
    } yield result

    println(
      s"Example Concurrent With Sleep: ${timeAndPrint(exampleConcurrentWithSleep.unsafeRunSync())}"
    )
  }
}
