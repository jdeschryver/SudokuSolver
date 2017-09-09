package solver

/**
 * @author Jan De Schryver <Jan.DeSchryver@bucephalus.be>
 */


data class Cell(var value: Int = -1, val editable: Boolean = true, var possibilities: MutableList<Int> = mutableListOf(1,2,3,4,5,6,7,8,9))

class Sudoku {
    private val matrix: Array<Cell> = Array(9 * 9) { Cell() }

    fun getCell(c: Pair<Int, Int>): Cell = matrix[matrixIndex(c.first, c.second)]

    // Coordinates top left are (0,0), bottom right (8,8)
    private fun matrixIndex(x: Int, y: Int) = x * 9 + y
}