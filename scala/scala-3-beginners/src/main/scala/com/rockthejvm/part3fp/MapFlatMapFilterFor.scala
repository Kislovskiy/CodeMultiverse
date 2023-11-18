package com.rockthejvm.part3fp

object MapFlatMapFilterFor {

  //standard list
  private val aList = List(1, 2, 3) // [1] -> [2] -> [3] -> Nil // [1, 2, 3]
  val firstElement: Int = aList.head
  val restOfElements: Seq[Int] = aList.tail

  // map
  val anIncrementedList: Seq[Int] = aList.map(_ + 1)

  // filter
  private val onlyOddNumbers: Seq[Int] = aList.filter(_ % 2 != 0)

  // flatMap
  private val toPair = (x: Int) => List(x, x + 1)
  val aFlatMappedList: Seq[Int] = aList.flatMap(toPair) // [1,2, 2,3, 3,4]

  // All the possible combinations of all the elements of those lists, in the format "1a - black"
  private val numbers = List(1, 2, 3, 4)
  private val chars = List('a', 'b', 'c', 'd')
  private val colors = List("black", "white", "red")

  /*
    lambda = num => chars.map(char => s"$num$char")
    [1, 2, 3, 4].flatMap(lambda) = ["1a", "1b", "1c", "1d", "2a", "2b", "2c", "2d", ... ]
    lambda(1) = chars.map(char => s"1$char") = ["1a", "1b", "1c", "1d"]
    lambda(2) = .. = ["2a", "2b", "2c", "2d"]
    lambda(3) = ..
    lambda(4) = ..
   */

  private val combinations = numbers.withFilter(_ % 2 == 0).flatMap(number => chars.flatMap(char => colors.map(color => s"$number$char - $color")))

  // for-comprehension = IDENTICAL to flatMap + map chains
  private val combinationsFor = for {
    number <- numbers if number % 2 == 0// generator
    char <- chars
    color <- colors
  } yield s"$number$char - $color" // an EXPRESSION

  // for-comprehensions with Unit
  // if foreach


  def main(args: Array[String]): Unit = {
    numbers.foreach(println)
    for {
      num <- numbers
    } println(num)
    println(combinations)
    println(combinationsFor)
  }
}
