//> using resourceDir ./
import scala.io.Source

object printInput extends App {
  private val inputs =
    Source.fromResource("input.txt").getLines.map(_.toInt).toSeq
  println(inputs.mkString(","))
}
