package practice

object playground {

  private def extractInformation2(input: String): (String, String) = {
    val cityPattern = """^([a-zA-Z_]+)-([a-zA-Z0-9]+)$""".r

    cityPattern.findFirstMatchIn(input) match {
      case Some(matchResult) =>
        val location = matchResult.group(1)
        val node = matchResult.group(2)
        (location, node)
      case None =>
        println(s"Input '$input' does not match the expected pattern.")
        ("NA", "NA")
    }
  }

  private def extractInformation(input: String): (String, String) = {
    val cityPattern = """^([a-zA-Z_]+)-([a-zA-Z0-9]+)$""".r

    if (cityPattern.matches(input)) {
      input.split("-", 2) match {
        case Array(location, node) => (location, node)
        case _ => ("NA", "NA")
      }
    }
    else {
      ("NA", "NA")
    }
  }
  def main(args: Array[String]): Unit = {
    println(extractInformation2("Amsterdam-Server2"))
    println(extractInformation2("Amsterdam-"))
    println(extractInformation2("-"))
    println(extractInformation2(""))
    println(extractInformation2("Los_Angeles-Server5"))

  }
}
