package api

interface Sudoku {

    /**
     * return whether the sudoku has been solved or not,
     * returning true implies [toArray] will return the solved sudoku
     *
     * @see toArray
     */
    fun solve(): Boolean

    /**
     * return an array containing all 81 cells row by row
     */
    fun toArray(): Array<Int?>

    /**
     * @param values a [List] containing [Triple]s of the form <row, column, value>
     *     of the elements to be initialised into the sudoku.
     */
    fun fill(values: List<Triple<Int, Int, Int>>)

    /**
     *
     */
    operator fun get(row: Int, col: Int) : Cell

    /**
     *
     */
    operator fun set(row: Int, col: Int, cell: Cell)
}
