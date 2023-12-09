import scala.util.Using
object Day4 {

  private val filename = "src/main/scala/resources/Day4.txt"

  private def getScore(line: String): Double =
    val inputPattern = """Card\s+\d+: ([\d\s]+) \| ([\d\s]+)""".r
    val matchData = inputPattern.findFirstMatchIn(line)
    matchData match {
      case Some(matched) =>
        val winningNumbers = matched.group(1).trim.split("\\s+").map(_.toInt).toSet
        val testNumbers = matched.group(2).trim.split("\\s+").map(_.toInt).toSet
        val commonNumbersSize = winningNumbers.intersect(testNumbers).size
        if (commonNumbersSize == 0) 0 else if (commonNumbersSize == 1) 1 else scala.math.pow(2, commonNumbersSize - 1)
    }

  private def readFileAsSeq(filename: String) =
    Using(io.Source.fromFile(filename)) {
      _.getLines.toSeq
    }.getOrElse(Seq.empty)

  def main(args: Array[String]): Unit = {
    val lines = readFileAsSeq("src/main/scala/resources/Day4.txt")
    val totalScore = lines.map(getScore).sum
    println(s"Part 1: ${totalScore.toInt}")
  }
}
