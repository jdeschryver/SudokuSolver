package solver

import org.apache.commons.lang3.StringUtils

/**
 * @author Jan De Schryver <Jan.DeSchryver@bucephalus.be>
 */
class Sudoku {
    private var matrix: Array<Cell> = Array(9 * 9) { ECell() }

    fun getCell(c: Pair<Int, Int>): Cell = matrix[matrixIndex(c.first, c.second)]

    // Coordinates top left are (0,0), bottom right (8,8)
    private fun matrixIndex(x: Int, y: Int) = x * 9 + y

    fun init(setup: List<Triple<Int, Int, Int>>){
        for ((x,y, value) in setup){
            matrix[matrixIndex(x,y)] = SCell(value)
        }
    }

    fun deletePossibility(c: Pair<Int, Int>, value: Int) {
        val blockX = Math.floor(c.first / 3.0).toInt() * 3
        val blockY = Math.floor(c.second / 3.0).toInt() * 3

        repeat(9) { x ->
            val range = controlLoop(c, x, blockX, blockY)
            for (y in range.first..range.second) {
                val cell = getCell(Pair(x, y))
                if( cell is ECell){
                    cell.possibilities.remove(value)
                }
            }
        }
    }

    private fun controlLoop(c: Pair<Int, Int>, x: Int, blockX: Int, blockY: Int): Pair<Int, Int> = when (x) {
        c.first -> Pair(0, 8)
        in blockX..blockX + 3 -> Pair(blockY, blockY + 3)
        else -> Pair(c.second, c.second)
    }

    fun copy() : Sudoku{
        val sudoku = Sudoku()
        val matrixCopy = mutableListOf<Cell>()
        matrix.forEach { it -> matrixCopy.add(it.copy())}
        sudoku.matrix = matrixCopy.toTypedArray()

        return sudoku
    }


    fun toPrettyString(): String {
        val line = StringUtils.repeat('-', 5)
        val center = StringUtils.repeat(line, "+", 3)+"\n"

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
