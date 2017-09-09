package solver

import org.apache.commons.lang3.StringUtils

/**
 * @author Jan De Schryver <Jan.DeSchryver@bucephalus.be>
 */


data class Cell(var value: Int = -1, val editable: Boolean = true, var possibilities: MutableList<Int> = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9))

class Sudoku {
    //private val matrix: MutableList<Cell> = mutableListOf()
    private val matrix: Array<Cell> = Array(9 * 9) { Cell() }

    /*
    init {
        repeat(9) { x ->
            repeat(9) { y ->
                matrix[matrixIndex(x, y)] = Cell()
            }
        }
    }
    */

    fun getCell(c: Pair<Int, Int>): Cell? = matrix[matrixIndex(c.first, c.second)]

    // Coordinates top left are (0,0), bottom right (8,8)
    private fun matrixIndex(x: Int, y: Int) = x * 9 + y

    fun toPrettyString(): String {
        val line = StringUtils.repeat('-', 5)
        val center = "$line+$line+$line\n"

        var prettyString = ""
        repeat(9) { i ->
            repeat(9) { j ->
                val cellVal = matrix[matrixIndex(i, j)].value
                prettyString += if (cellVal < 0) '.' else cellVal
                prettyString += if ((j + 1) % 3 == 0 && j != 8) '|' else ' '
            }
            prettyString += '\n'
            if ((i + 1) % 3 == 0 && i != 8) prettyString += center
        }
        return prettyString
    }
}