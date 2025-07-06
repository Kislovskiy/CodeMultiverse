//> using resourceDir "resources"
//> using nativeEmbedResources true
//> using nativeLto "thin"
//> using nativeMode "release-fast"

import scala.io.Source

@main def main(): Unit = {
  val inputs = Source.fromResource("input.txt").getLines.map(_.toInt).toSeq
  println(inputs.mkString("|"))
}
