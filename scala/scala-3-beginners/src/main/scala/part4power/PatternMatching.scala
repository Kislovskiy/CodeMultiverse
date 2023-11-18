package part4power

import scala.util.Random

object PatternMatching {

  // switch on steroids
  val random = new Random()
  private val aValue = random.nextInt(100)

  private val description = aValue match {
    case 1 => "the first"
    case 2 => "the second"
    case 3 => "the third"
    case _ => s"something else: $aValue"
  }

  // decompose values
  private case class Person(name: String, age: Int)
  private val bob = Person("Bob", 20)

  private val greeting: String = bob match {
    case Person(n, a) if a < 18 => s"Hi, my name is $n and I'm $a years old."
    case Person(n, a) => s"Hello there, my name is $n and I'm not allowed to say my age."
    case _ => "I don't know who I am."
  }

  /*
    Patterns are matched in order: put the most specific patterns first.
    What if no cases match? MatchError
    What's the type returned? The lowest common ancestor of all types on the RHS of each branch
   */

  // Pattern Matching on sealed hierarchies
  private sealed class Animal
  private case class Dog(breed: String) extends Animal
  private case class Cat(meowStyle: String) extends Animal

  private val anAnimal: Animal = Dog("Terra Nova")
  val animalPM: String = anAnimal match {
    case Dog(someBreed) => "I've detected a dog"
    case Cat(meow) => "I've detected a cat"
  }

  /*
    Exercise
    show(Sum(Number(2), Number(3))) = "2 + 3"
    show(Sum(Sum(Number(2), Number(3)), Number(4))) = "2 + 3 + 4"
    show(Prod(Sum(Number(2), Number(3)), Number(4))) = "(2 + 3) * 4"
    show(Sum(Prod(Number(2), Number(3)), Number(4))) = "2 * 3 + 4"
   */
  private sealed trait Expr
  private case class Number(n: Int) extends Expr
  private case class Sum(e1: Expr, e2: Expr) extends Expr
  private case class Prod(e1: Expr, e2: Expr) extends Expr

  private def show(expr: Expr): String = expr match {
    case Number(n) => s"$n"
    case Sum(left, right) => show(left) + " + " + show(right)
    case Prod(left, right) =>
      def maybeShowParentheses(exp: Expr) = exp match {
        case Prod(_, _) => show(exp)
        case Number(_) => show(exp)
        case Sum(_, _) => s"(${show(exp)})"
      }

      maybeShowParentheses(left) + " * " +  maybeShowParentheses(right)
  }

  def main(args: Array[String]): Unit = {
    println(description)
    println(greeting)
    println(show(Sum(Number(2), Number(3))))
    println(show(Sum(Sum(Number(2), Number(3)), Number(4))))
    println(show(Prod(Sum(Number(2), Number(3)), Number(4))))
    println(show(Sum(Prod(Number(2), Number(3)), Number(4))))
  }
}
