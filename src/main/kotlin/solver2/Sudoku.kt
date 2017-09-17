package solver2

import org.apache.commons.lang3.StringUtils

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

    fun combine() = rowC.filter { it in colC }.filter { it in squareC }
}

class Sudoku(values: List<Triple<Int, Int, Int>>) {

    private val board = Array<Cell>(9 * 9) { ECell() }
    private val rowPossibilities = Array(9) { (1..9).toHashSet() }
    private val colPossibilities = Array(9) { (1..9).toHashSet() }
    private val squarePossibilities = Array(9) { (1..9).toHashSet() }

    var isValid: Boolean = true
        private set

    init {
        values.forEach { (row, col, value) ->
            this[row, col] = SCell(value)
            if (!possibilities(row, col).remove(value)) {
                isValid = false
            }
        }
    }

    fun toArray() = board.map { it.value }.toTypedArray()

    fun solve(): Boolean {
        val index = nextECell()
        return index?.let {
            isValid && solve(index)
        } ?: false
    }

    private fun solve(index: Int): Boolean {
        val (row, col) = indexToCoord(index)
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

    private fun indexToCoord(index: Int) = index / 9 to index % 9

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

    private operator fun get(row: Int, col: Int) = board[row * 9 + col]

    private operator fun set(row: Int, col: Int, cell: Cell) {
        val index = row * 9 + col
        //updateConstraints(row, col, board[index].value, cell.value)
        board[index] = cell
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
        prettyString += "rowPossibilities: ${rowPossibilities.contentDeepToString()}\n" +
                "colPossibilities: ${colPossibilities.contentDeepToString()}\n" +
                "squarePossibilities: ${squarePossibilities.contentDeepToString()}\n"
        return prettyString
    }

}
