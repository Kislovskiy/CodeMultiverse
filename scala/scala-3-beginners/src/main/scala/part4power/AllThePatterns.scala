package part4power

object AllThePatterns {

  object MySingleton

  // 1 - constants
  val someValue: Any = "Scala"
  val constants = someValue match {
    case 42 => "a number"
    case "Scala" => "THE Scala"
    case true => "the truth"
    case MySingleton => "a singleton object"
  }

  // 2 - match anything
  val matchAnythingVar = someValue match {
    case something => s"I've matched anything, it's $something"
  }

  val matchAnything = someValue match {
    case _ => "I can match anything at all"
  }

  // 3 - tuples
  val aTuple = (1, 4)
  val matchTuple = aTuple match {
    case (1, somethingElse) => s"A tuple with 1 and $somethingElse"
    case (something, 2) => "A tuple with 2 as its second field"
  }

  // PM structures can be NESTED
  val nestedTuple = (1, (2, 3))
  val matchNestedTuple = nestedTuple match {
    case (_, (2, v)) => "A nested tuple ..."
  }

  val anOption: Option[Int] = Option(2)
  val matchOption = anOption match {
    case None => "an empty option"
    case Some(value) => s"non-empty, got $value"
  }

  // 5 - list patterns
  val aStandardList = List(1, 2, 3, 42)
  val matchStandardList = aStandardList match {
    case List(1, _, _, _) => "List with 4 elements, first is 1"
    case List(1, _*) => "List starting with 1"
    case List(1, 2, _) :+ 42 => "list ending in 42"
    case head :: tail => "deconstructed list"
  }

  // 6 - type specifiers
  val unknown: Any = 2
  val matchTyped = unknown match {
    case anInt: Int => s"I matched an int, I can add 2 to it: ${anInt + 2}"
    case aString: String => "I matched a String"
    case _: Double => "I matched a double I don't care about"
  }

//  7 - name binding
//  val bindingNames = aList match {
//    case Cons(head, rest @ Cons(_, tail)) => s"Can use $rest"
//  }

// 8 - chained patterns
//  val multiMatch = aList match {
//    case Empty() | Cons(0, _) => "an empty list to me"
//    case _ => "anything else"
//  }

// 9 - if guards
//  val secondElementSpecial = aList match {
//    case Cons(_, Cons(SpecialElement, _)) if specialElement > 5 => "second element is big enough"
//    case _ => "anything else"
//  }

  // have anti-pattern
  val aSimpleInt = 45
  val isEven_bad = aSimpleInt match {
    case n if n % 2 == 0 => true
    case _ => false
  }
  val isEven_bad_v2 = if (aSimpleInt % 2 == 0) true else false
  // better
  val isEven = aSimpleInt % 2 == 0

  val numbers = List(1, 2, 3, 4)
  val numbersMatch = numbers match {
    case listOfStrings: List[String] => "a list of strings"
    case listOfInts: List[Int] => "a list of numbers"
  }

  /*
  Pattern matching is running at runtime
  - reflection (feature of JVM)
  - generic types are erased at runtime
    List[String] => List
    List[String] => List
    Function1[Int, String] => Functional1
  JVM doesn't have [String] or [Int] at runtime
   */

  def main(args: Array[String]): Unit = {
    println(numbersMatch)
  }
}
