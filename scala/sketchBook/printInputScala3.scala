import scala.util.{Try, Using, Success, Failure}

@main def printInputScala3(): Unit = {
  def readFileAsSeq(filename: String): Try[Seq[String]] =
    Using(io.Source.fromFile(filename)) { _.getLines.toSeq }

  readFileAsSeq("resources/input.txt") match {
    case Success(value)     => println(value.mkString("|"))
    case Failure(exception) => println("Couldn't get lines.")
  }
}
