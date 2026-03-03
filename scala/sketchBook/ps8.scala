//> using dep org.scalameta::munit::1.2.0

import Padding.{padLines, padLinesWithLocalFunction, padLinesWithLocalFunctionSimplified}

object Padding:
  def padLines(text: String, minWidth: Int): String =
    val paddedLines =
      for line <- text.linesIterator yield
        padLine(line, minWidth)
    paddedLines.mkString("\n")

  private def padLine(line: String, minWidth: Int): String =
    if line.length >= minWidth then line
    else line + "*" * (minWidth - line.length)

  /**
   * Instead of defining private method as in Java, in Scala you can define a local function.
   * As a local function, `padLine` is in scope inside `padLinesWithLocalFunction`, but inaccessible outside.
   */
  def padLinesWithLocalFunction(text: String, minWidth: Int): String =
    def padLine(line: String, minWidth: Int): String =
      if line.length >= minWidth then line
      else line + "*" * (minWidth - line.length)

    val paddedLines =
      for line <- text.linesIterator yield
        padLine(line, minWidth)
    paddedLines.mkString("\n")

  def padLinesWithLocalFunctionSimplified(text: String, minWidth: Int): String =
    /**
     * There is no need to pass `width` to a local function, because the local function can access the parameters of
     * their enclosing function.
     */
    def padLine(line: String): String =
      if line.length >= minWidth then line
      else line + "*" * (minWidth - line.length)

    val paddedLines =
      for line <- text.linesIterator yield
        padLine(line)
    paddedLines.mkString("\n")

end Padding

@main def ps_8(): Unit =
  println(padLines("hello\nhello" * 2, 12))

class ps8Test extends munit.FunSuite:
  private val padding = 12
  private val inputString = "hello\nhello" * 2
  private val expectedString =
    """
      |hello*******
      |hellohello**
      |hello*******
      |""".stripMargin

  test("padLines"):
    assertNoDiff(padLines(inputString, padding), expectedString)

  test("padLinesWithLocalFunction"):
    assertNoDiff(padLinesWithLocalFunction(inputString, padding), expectedString)

  test("padLinesWithLocalFunctionSimplified"):
    assertNoDiff(padLinesWithLocalFunctionSimplified(inputString, padding), expectedString)

  test("functionLiteralAndFunctionValues"):
    // (x: Int) => x + 1 is a function literal
    // function values are objects, so you can store them in variables, they are functions, so they can be invoked using
    // the usual parentheses function-call notation
    val increase = (x: Int) => x + 1
    val addTwo = (x: Int) =>
      val increment = 2
      x + increment
    val positive = (x: Int) => x > 0
    val someNumbers = List(-11, -10, -5, 0, 5, 10)
    assert(increase(10) == 11)
    assert(addTwo(10) == 12)
    assert(someNumbers.filter((x: Int) => x > 0) == List(5, 10))
    assert(someNumbers.filter(positive) == List(5, 10))
    
  test("partiallyAppliedFunctions"):
    def sum(a: Int, b: Int, c: Int) = a + b + c
    val a = sum
    val b = sum(1, _, 3)
    assert(a(1, 2, 3) == 6)
    assert(b(2) == 6)
    assert(b(3) == 7)
end ps8Test
