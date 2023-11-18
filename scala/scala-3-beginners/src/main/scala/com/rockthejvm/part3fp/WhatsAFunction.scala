package com.rockthejvm.part3fp

object WhatsAFunction {
  // Functional Programming: functions are "first-class" citizens
  // JVM -> doesn't support functions by default

  private trait MyFunction[A, B] {
    def apply(arg: A): B
  }

  private val doubler = new MyFunction[Int, Int] {
    override def apply(arg: Int): Int = arg * 2
  }

  private val meaningOfLife = 42
  val meaningDoubled: Int = doubler(meaningOfLife) // doubler.apply(meaningOfLife)

  // function types
  // (Int => Int) === Function4[Int, Int]
  private val doublerStandard = new (Int => Int) {
    override def apply(arg: Int): Int = arg * 2
  }
  val meaningDoubled_v2: Int = doublerStandard(meaningOfLife)

  private val adder = new ((Int, Int) => Int) {
    override def apply(a: Int, b: Int): Int = a + b
  }
  val anAddition: Int = adder(2, 67)

  private val concatenator = new ((String, String) => String) {
    override def apply(a: String, b: String): String = a + b
  }

  private val superAdder = new (Int => Int => Int) {
    override def apply(x: Int): Int => Int = (y: Int) => x + y
  }

  private val adder2 = superAdder(2)
  val anAddition_v2: Int = adder2(67) // 69
  // currying
  val anAddition_v3: Int = superAdder(2)(67)

  // function values != methods

  def main(args: Array[String]): Unit = {
    println(concatenator("Hello ", "world"))
  }
}
