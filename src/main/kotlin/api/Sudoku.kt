package api

abstract class Sudoku {

    private val board = Array<Cell>(9 * 9) { ECell() }

    /**
     * return whether the sudoku has been solved or not,
     * returning true implies [toArray] will return the solved sudoku
     *
     * @see toArray
     */
    abstract fun solve(): Boolean

    /**
     * return an array containing all 81 cells row by row
     */
    fun toArray() = board.map { it.value }.toTypedArray()

    /**
     * @param values a [List] containing [Triple]s of the form <row, column, value>
     *     of the elements to be initialised into the sudoku.
     */
    abstract fun fill(values: List<Triple<Int, Int, Int>>)

    /**
     *
     */
    protected operator fun get(row: Int, col: Int) = board[row * 9 + col]

    /**
     *
     */
    protected operator fun set(row: Int, col: Int, cell: Cell) {
        val index = row * 9 + col
        board[index] = cell
    }
}
