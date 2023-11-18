package com.rockthejvm.part3fp

object TuplesMaps {
  // tuples = finite ordered "lists" / group of values under the same "big" value
  val aTuple: (Int, String) = (2, "rock the jvm") // Tuple2[Int, String] == (Int, String)
  val firstField: Int = aTuple._1
  private val aCopiedTuple = aTuple.copy(_1 = 54)

  // tuples of 2 elements
  val aTuple_v2: (Int, String) = 2 -> "rock the jvm" // IDENTICAL to (2, "rock the jvm")

  // maps: keys -> values
  val aMap: Map[Nothing, Nothing] = Map()

  val phonebook: Map[String, Int] = Map(
    "Jim" -> 555,
    "Daniel" -> 789,
    "Jane" -> 123
  ).withDefaultValue(-1)

  // core APIs
  private val phonebookHasDaniel = phonebook.contains("Daniel")
  private val marysPhoneNumber = phonebook("Mary") // crash with an exception without .withDefaultValue(-1)

  // add a pair
  private val newPair = "Mary" -> 678
  val newPhonebook: Map[String, Int] = phonebook + newPair

  // remove a key
  val phoneBookWithoutDaniel: Map[String, Int] = phonebook - "Daniel" // new map

  // list -> map
  private val linearPhonebook = List(
    "Jim" -> 555,
    "Daniel" -> 789,
    "Jane" -> 123
  )

  val phonebook_v2: Map[String, Int] = linearPhonebook.toMap

  // map -> linear collection
  val linearPhonebook_v2: List[(String, Int)] = phonebook.toList // toSeq, toVector, toArray, toSet

  // map, flatMap, filter
  /*
    Map("Jim" -> 123, "jiM" -> 999) don't know which JIM will be present in the final map => Map("JIM" -> ??? )
   */
  val aProcessedPhonebook: Map[String, Int] = phonebook.map(pair => (pair._1.toUpperCase(), pair._2))

  // filtering keys
  val noJs: Map[String, Int] = phonebook.view.filterKeys(_.startsWith("J")).toMap

  // mapping values
  val prefixNumbers: Map[String, String] = phonebook.view.mapValues(number => s"0255-$number").toMap

  // other collections can create maps
  private val names = List("Bob", "James", "Angela", "Mary", "Daniel", "Jim")
  private val nameGroupings = names.groupBy(name => name.charAt(0)) // Map[Char, List[String]]


  def main(args: Array[String]): Unit = {
    println(phonebook)
    println(phonebookHasDaniel)
    println(marysPhoneNumber)
    println(nameGroupings)
  }
}
