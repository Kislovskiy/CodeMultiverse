package com.rockthejvm.part3fp

object AnonymousFunctions {

  // instances of FunctionN
  val doubler: Int => Int = new (Int => Int) {
    override def apply(x: Int): Int = x * 2
  }

  // lambdas = anonymous function instances
  val doubler_v2: Int => Int = (x: Int) => x * 2
  val adder: (Int, Int) => Int = (x: Int, y: Int) => x + y // new Function2[Int, Int, Int] {override def apply...}

  // zero-arg functions
  private val justDoSomething: () => Int = () => 45
  val anInvocation: Int = justDoSomething()

  // alternative syntax with curly braces
  val stringToInt: String => Int = { (str: String) =>
    // implementation: code block
    str.toInt
  }

  val stringToIntBoring: String => Unit = (str: String) => {
    // code block
  }

  // type inference
  val doubler_v3: Int => Int = x => x * 2
  val adder_v2: (Int, Int) => Int = (x, y) => x + y

  // shortest lambdas
  val doubler_v4: Int => Int = _ * 2 // x => x * 2
  val adder_v3: (Int, Int) => Int = _ + _ // (x, y) => x + y
  // each underscore is a different argument, you can't reuse them

  private val superAdder = new(Int => Int => Int) {
    override def apply(x: Int): Int => Int = (y: Int) => x + y
  }

  private val superAdder_v2 = (x: Int) => (y: Int) => x + y
  private val adding2 = superAdder_v2(2) // (y: Int) => 2 + y 
  val addingInvocation: Int = adding2(43) // 45
  val addinginvocation_v2: Int = superAdder_v2(2)(43)

  def main(args: Array[String]): Unit = {
    println(justDoSomething)
    println(justDoSomething())
  }
}
