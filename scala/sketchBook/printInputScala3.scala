//> using resourceDir ./
import scala.io.Source

@main
def main(): Unit = {
  val inputs = Source.fromResource("input.txt").getLines.map(_.toInt).toSeq
  println(inputs.mkString("|"))
}
