/** Configuration for multiplication table generation.
  *
  * @param size
  *   the dimensions of the multiplication table (size × size)
  * @param columnWidth
  *   columnWidth the width of each column in characters for alignment
  * @example
  *   {{{
  * val config = TableConfig(size = 12, columnWidth = 5)
  * // Creates a 12x12 table with 5-character wide columns
  *   }}}
  */
case class TableConfig(size: Int = 10, columnWidth: Int = 4) {
  require(size > 0, "Table size must be positive")
  require(columnWidth > 0, "Column width must be positive")
}

/** Factory object for generating multiplication tables.
  *
  * Provides methods to generate formatted multiplication tables with
  * configurable size and formatting options.
  *
  * @example
  *   {{{
  * // Generate default 10x10 table
  * val table = MultiplicationTable.generate()
  *
  * // Generate custom 5x5 table with wider columns
  * val customTable = MultiplicationTable.generate(
  *   TableConfig(size = 5, columnWidth = 6)
  * )
  *   }}}
  */
object MultiplicationTable {

  /** Generates a formatted multiplication table as a string.
    *
    * Creates a multiplication table where each cell contains the product of its
    * row and column indices, formatted with consistent spacing.
    *
    * @param config
    *   configuration specifying table dimensions and formatting
    * @return
    *   formatted multiplication table as a multi-line string
    *
    * @example
    *   {{{
    * val table = MultiplicationTable.generate(TableConfig(3, 4))
    * println(table)
    * // Output:
    * //    1   2   3
    * //    2   4   6
    * //    3   6   9
    *   }}}
    */
  def generate(config: TableConfig = TableConfig()): String = {
    val formatter = new TableFormatter(config.columnWidth)
    val data = generateTableData(config.size)
    formatter.formatTable(data)
  }

  /** Generates the raw numerical data for a multiplication table.
    *
    * @param size
    *   the dimensions of the table (size × size)
    * @return
    *   a sequence of sequences containing multiplication results
    * @note
    *   This method is private as it's an implementation detail
    */
  private def generateTableData(size: Int): Seq[Seq[Int]] =
    for row <- 1 to size yield for col <- 1 to size yield row * col
}

/** Formats multiplication table data into aligned string representation.
  *
  * Handles the presentation logic for multiplication tables, ensuring
  * consistent column alignment and spacing.
  *
  * @param columnWidth
  *   the width in characters for each column
  * @example
  *   {{{
  * val formatter = new TableFormatter(columnWidth = 5)
  * val data = Seq(Seq(1, 2, 3), Seq(2, 4, 6))
  * val result = formatter.formatTable(data)
  * println(result)
  * // Output:
  * //    1    2    3
  * //    2    4    6
  *   }}}
  */
class TableFormatter(columnWidth: Int) {
  require(columnWidth > 0, "Column width must be positive")

  def formatTable(data: Seq[Seq[Int]]): String =
    data.map(formatRow).mkString("\n")

  private def formatRow(row: Seq[Int]): String =
    row.map(formatCell).mkString

  private def formatCell(value: Int): String = {
    val str = value.toString
    " " * math.max(0, columnWidth - str.length) + str
  }
}

/** Main entry point for the multiplication table application.
  *
  * Demonstrates usage of the MultiplicationTable generator with default
  * configuration (10×10 table, 4-character columns).
  *
  * @example
  *   Run with: `scala-cli run multiplicationTable.scala`
  * @note
  *   to generate documentation run:
  *   `scala-cli doc multiplicationTable.scala -o scaladoc-output`
  */
@main def multiTable(): Unit = {
  val table = MultiplicationTable.generate()
  println(table)
}
