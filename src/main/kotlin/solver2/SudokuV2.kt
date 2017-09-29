package solver2

import api.*

data class Possibilities(private val rowC: MutableSet<Int>, private val colC: MutableSet<Int>, private val squareC: MutableSet<Int>) {
    fun add(i: Int): Boolean {
        val b1 = rowC.add(i)
        val b2 = colC.add(i)
        val b3 = squareC.add(i)
        return b1 && b2 && b3
    }

    fun remove(i: Int): Boolean {
        val b1 = rowC.remove(i)
        val b2 = colC.remove(i)
        val b3 = squareC.remove(i)
        return b1 && b2 && b3
    }

    fun combine() = rowC.intersect(colC).intersect(squareC)
}

class SudokuV2 : Sudoku {

    private val board = Array<Cell>(9 * 9) { ECell() }
    private val rowPossibilities = Array(9) { (1..9).toHashSet() }
    private val colPossibilities = Array(9) { (1..9).toHashSet() }
    private val squarePossibilities = Array(9) { (1..9).toHashSet() }

    private var isValid: Boolean = true

    override fun fill(values: List<Triple<Int, Int, Int>>) {
        values.forEach { (row, col, value) ->
            this[row, col] = SCell(value)
            if (!possibilities(row, col).remove(value)) {
                isValid = false
            }
        }
    }

    override fun solve(): Boolean {
        val index = nextECell()
        return index?.let {
            isValid && solve(index)
        } == true
    }

    private fun solve(index: Int): Boolean {
        val (row, col) = indexToCoordinates(index)
        val possibilities = possibilities(row, col).combine()
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

    override fun toArray() = board.map { it.value }.toTypedArray()

    private fun indexToCoordinates(index: Int) = index / 9 to index % 9

    private fun nextECell(index: Int = -1) = ((index + 1)..(board.size - 1)).firstOrNull { board[it] is ECell }

    private fun possibilities(row: Int, col: Int) = Possibilities(
            rowPossibilities[row],
            colPossibilities[col],
            squarePossibilities[(row / 3) * 3 + (col / 3)]
    )

    private fun updateConstraints(row: Int, col: Int, old: Int?, new: Int?) {
        if (old == new) return
        val poss = possibilities(row, col)
        old?.let { poss.add(old) }
        new?.let { poss.remove(new) }
    }

    private operator fun set(row: Int, col: Int, value: Int?) {
        val cell = this[row, col]
        when (cell) {
            is ECell -> {
                updateConstraints(row, col, cell.value, value)
                cell.value = value
            }
            is SCell -> throw UnsupportedOperationException("Cannot edit given Cells.")
        }
    }

    override operator fun get(row: Int, col: Int) = board[row * 9 + col]

    override operator fun set(row: Int, col: Int, cell: Cell) {
        val index = row * 9 + col
        board[index] = cell
    }
}
