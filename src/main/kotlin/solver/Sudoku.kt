package solver

import api.*
import api.Sudoku
import org.apache.commons.lang3.StringUtils

/**
 * @author Jan De Schryver <Jan.DeSchryver@bucephalus.be>
 */
class Sudoku: Sudoku {

    private val board = Array<Cell>(9 * 9) { ECell() }
    private val rowVector = Array(9) { BitVector() }
    private val colVector = Array(9) { BitVector() }
    private val squareVector = Array(9) { BitVector() }

    override fun fill(values: List<Triple<Int, Int, Int>>) {
        values.forEach { (row, col, value) ->
            this[row, col] = SCell(value)
        }
    }

    override fun solve(): Boolean {
        val index = nextECell()
        return index?.let {
            solve(index)
        } ?: false
    }

    private fun solve(index: Int): Boolean {
        val (row, col) = indexToCoord(index)
        val vectors =  vectorsOfCell(row, col)
        val possibilities = possibilities(vectors.first, vectors.second, vectors.third)

        if (possibilities.isEmpty())
            return false

        val next = nextECell(index)

        possibilities.forEach { entry ->
            this[row, col] = entry
            next?.let {
                if (solve(it))
                    return true
            } ?: return true
        }
        this[row, col] = null
        return false
    }

    private fun vectorsOfCell(row: Int, col: Int) = Triple(
            rowVector[row],
            colVector[col],
            squareVector[(row / 3) * 3 + (col / 3)])


    private fun nextECell(index: Int = -1) = ((index + 1)..(board.size - 1)).firstOrNull { board[it] is ECell }

    private fun indexToCoord(index: Int) = index / 9 to index % 9

    override fun toArray() = board.map { it.value }.toTypedArray()

    override operator fun get(row: Int, col: Int) = board[row * 9 + col]

    override operator fun set(row: Int, col: Int, cell: Cell) {
        val index = row * 9 + col
        board[index] = cell
    }

    private operator fun set(row: Int, col: Int, value: Int?) {
        val cell = this[row, col]
        when (cell) {
            is ECell -> {
                vectorsOfCell(row, col).toList().forEach{ it.set(value); it.unSet(cell.value)}
                cell.value = value
            }
            is SCell -> throw UnsupportedOperationException("Cannot edit given Cells.")
        }
    }

    fun toPrettyString(): String {
        val line = StringUtils.repeat('-', 5)
        val center = StringUtils.repeat(line, "+", 3) + "\n"

        var prettyString = ""
        repeat(9) { row ->
            repeat(9) { col ->
                prettyString += this[row, col].value ?: '.'
                prettyString += if ((col + 1) % 3 == 0 && col != 8) '|' else ' '
            }
            prettyString += '\n'
            if ((row + 1) % 3 == 0 && row != 8) prettyString += center
        }
        prettyString += "rowVector: ${rowVector.contentDeepToString()}\n" +
                "colVector: ${colVector.contentDeepToString()}\n" +
                "squareVector: ${squareVector.contentDeepToString()}\n"
        return prettyString
    }
}
