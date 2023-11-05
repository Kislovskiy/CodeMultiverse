package com.rockthejvm.part3fp

import scala.annotation.tailrec

object HOFsCurrying {

  // higher order functions (HOFs)
  private val aHof: (Int, Int => Int) => Int = (x, func) => x + 1
  val anotherHof: Int => Int => Int = x => y => y + 2 * x

  val superFunction: (Int, (String, Int => Boolean) => Int) => Int => Int =
    (x, func) => y => x + y

  // examples: map, flatMap, filter

  // more examples:
  // f(f(f(...f())))
  @tailrec
  private def nTimes(f: Int => Int, n: Int, x: Int): Int =
    if (n <= 0) x
    else nTimes(f, n-1, f(x))

  private val plusOne = (x: Int) => x + 1
  private val tenThousand = nTimes(plusOne, 10_000, 0)

  /*
    ntv2(po, 3) =
    (x: Int) => ntv2(po, 2)(po(x)) = po(po(po(x)))
    ntv2(po, 2) =
    (x: Int) => ntv2(po, 1)(po(x)) = po(po(x))
    ntv2(po, 1) =>
    (x: Int) => ntv2(po, 0)(po(x)) = po(x)
  ntv2(po, 0) = (x: Int) => x


   */
  private def nTimes_v2(f: Int => Int, n: Int): Int => Int =
    if (n <= 0) (x: Int) => x
    else (x: Int) => nTimes_v2(f, n-1)(f(x))

  private val plusOneHundred: Int => Int = nTimes_v2(plusOne, 100) // po(po(po(..))) risks stackoverflow error for the big arg
  private val oneHundred: Int = plusOneHundred(0)

  // currying = HOFs returning function instances
  val superAdder: Int => Int => Int = (x: Int) => (y: Int) => x + y
  val add3: Int => Int = superAdder(3)
  val invokeSuperAdder: Int = superAdder(3)(100) // 103

  // curried methods = methods with multiple arg list
  private def curriedFormatter(fmt: String)(x: Double): String = fmt.format(x)

  private val standardFormat: Double => String = curriedFormatter("%4.2f") // (x: Double) => "%4.2f".format(x)
  private val preciseFormat: Double => String = curriedFormatter("%10.8f") // (x: Double) => "%10.8".format(x)


  private def toCurry[A, B, C](f: (A, B) => C): A => B =>  C =
    x => y => f(x, y)

  val superAdder_v2: Int => Int => Int = toCurry[Int, Int, Int](_ + _) // same as superAdder

  private def fromCurry[A, B, C](f: A => B => C): (A, B) => C =
    (x, y) => f(x)(y)

  private val simpleAdder = fromCurry(superAdder)

  private def compose[A, B, C](f: B => C, g: A => B): A => C =
    x => f(g(x))

  private def andThen[A, B, C](f: A => B, g: B => C): A => C =
    x => g(f(x))

  private val incrementor = (x: Int) => x + 1
  val doubler: Int => Int = (x: Int) => 2 * x
  private val composedApplication = compose(incrementor, doubler)
  private val aSequencedApplication = andThen(incrementor, doubler)

  def main(args: Array[String]): Unit = {
    println(aHof(2, x => x))
    println(oneHundred)
    println(standardFormat(Math.PI))
    println(preciseFormat(Math.PI))
    println(simpleAdder(2, 78))
    println(composedApplication(14)) // 29 = 2 * 14 + 1
    println(aSequencedApplication(14)) // 30 = (14 + 1) * 2
  }
}
