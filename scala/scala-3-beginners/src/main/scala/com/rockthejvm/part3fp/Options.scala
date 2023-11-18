package com.rockthejvm.part3fp

import scala.util.Random

object Options {

  // options = "collections" with at most one value
  val anOption: Option[Int] = Option(42)
  val anEmptyOption: Option[Int] = Option.empty

  // alt version
  val aPresentValue: Option[Int] = Some(4)
  val anEmptyOption_v2: Option[Int] = None

  // "standard" API
  val isEmpty: Boolean = anOption.isEmpty
  val innerValue: Int = anOption.getOrElse(90)
  private val anotherOption = Option(46)
  val aChainedOption: Option[Int] = anEmptyOption.orElse(anotherOption)

  // map, flatMap, filter, for
  private val anIncrementedOpiton: Option[Int] = anOption.map(_ + 1) // Some(43)
  val aFilteredOption: Option[Int] = anIncrementedOpiton.filter(_ % 2 == 0) // None
  val aFlatMappedOption: Option[Int] = anOption.flatMap(value => Option(value * 10)) // Some(420)




  // WHY options: work with unsafe API
  private def unsafeMethod(): String = null

  private def fallbackMethod(): String = "some valid result"

  // defensive style
  val stringLength: Int = {
    val potentialString = unsafeMethod()
    if (potentialString == null) -1
    else potentialString.length
  }

  // option-style: no need for null checks
  val stringLengthOption: Option[Int] = Option(unsafeMethod()).map(_.length)

  // use-case for orElse
  val someResult: Option[String] = Option(unsafeMethod()).orElse(Option(fallbackMethod()))

  // DESIGN
  private def betterUnsafeMethod(): Option[String] = None
  private def betterFallbackMethod(): Option[String] = Some("A valid result")

  private def betterChain: Option[String] = betterUnsafeMethod().orElse(betterFallbackMethod())

  // example: Map.get
  private val phoneBook = Map(
    "Daniel" -> 1234
  )
  val marysPhoneNumber: Option[Int] = phoneBook.get("Mary") // None
  // no need to crash, check for nulls or if Mary is present in the map

  /**
   * Exercise
   * Get the host and port from the config map,
   * try to open a connection,
   * print "Conn successful"
   * or "Conn failed"
   */

  val config: Map[String, String] = Map(
    // comes from elsewhere
    "host" -> "176.45.32.1",
    "port" -> "8801"
  )

  class Connection {
    def connect(): String = "Connection successful"
  }

  object Connection {
    val random = new Random()

    def apply(host: String, port: String): Option[Connection] =
      if (random.nextBoolean()) Some(new Connection)
      else None
  }

  // defensive style
  /*
    String host = config("host")
    String port = config("port")

    if (host != null)
      if (port != null)
        Connection conn = Connection.apply(host, port)
        if (conn != null)
          return conn.connect()
   */

  private val host: Option[String] = config.get("host")
  private val port: Option[String] = config.get("port")
  private val connection: Option[Connection] = host.flatMap(h => port.flatMap(p => Connection(h, p)))
  private val connectionStatus = connection.map(_.connect())

  // compact
  val connStatus_v2: Option[String] =
    config.get("host").flatMap(h =>
      config.get("port").flatMap(p =>
        Connection(h, p).map(_.connect())
      )
    )

  // for-comprehension
  val connStatus_v3: Option[String] = for {
    h <- config.get("host") // assuming that I can take the host from the get expression
    p <- config.get("port") // assuming that I can get the port from this expression
    conn <- Connection(h, p) // assuming that I can get a connection from Connection apply method
  } yield conn.connect() // I'm going to call connect on this object

  def main(args: Array[String]): Unit = {
    println(connectionStatus.getOrElse("Failed to establish connection"))
  }
}
