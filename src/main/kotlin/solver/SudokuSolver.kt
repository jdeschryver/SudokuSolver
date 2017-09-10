import solver.Sudoku

/**
 * @author Jan De Schryver <Jan.DeSchryver@bucephalus.be>
 */

class SudokuSolver {

    val sudoku = Sudoku()

    fun solve(c: Pair<Int, Int>): Boolean {
        if (c.second < 8) {
            val cell = sudoku.getCell(c)
            for (value in cell.possibilities) {
                cell.value = value
                deletePossibility(c, value)
                if (solve(nextCell(c))) {
                    return true
                }
            }
            return false
        }
        return true
    }

    private fun deletePossibility(c: Pair<Int, Int>, value: Int) {
        val blockX = Math.floor(c.first / 3.0).toInt() * 3
        val blockY = Math.floor(c.second / 3.0).toInt() * 3

        repeat(9) { x ->
            val range = controlLoop(c, x, blockX, blockY)
            for (y in range.first..range.second) {
                sudoku.getCell(Pair(x, y)).possibilities.remove(value)
            }
        }
    }

    private fun controlLoop(c: Pair<Int, Int>, x: Int, blockX: Int, blockY: Int): Pair<Int, Int> = when (x) {
        c.first -> Pair(0, 8)
        in blockX..blockX + 3 -> Pair(blockY, blockY + 3)
        else -> Pair(c.second, c.second)
    }

    private fun nextCell(c: Pair<Int, Int>): Pair<Int, Int> {
        var nextC: Pair<Int, Int>
        do {
            val x = (c.first + 1) % 9
            val y = if (x == 0) c.second + 1 else c.second
            nextC = Pair(x, y)
        } while (sudoku.getCell(nextC).editable)

        return nextC
    }
}

fun main(args: Array<String>) {
    println("Hello, world!")
    println(Sudoku().toPrettyString())
}