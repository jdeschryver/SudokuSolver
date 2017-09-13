import solver.Sudoku
import solver.ECell
import solver.SCell

/**
 * @author Jan De Schryver <Jan.DeSchryver@bucephalus.be>
 */

object SudokuSolver {

    fun solve(s: Sudoku) {
        val n = nextCell(s, 0 to 0)
        solve(s, n)
    }

    fun solve(s: Sudoku, c: Pair<Int, Int>): Boolean {
        var sudoku = s
        if (c.second < 8) {
            val cell = sudoku.getCell(c)
            if(cell is ECell) {
                val clone = ArrayList(cell.possibilities)
                for (value in clone) {
                    val oldSudoku = sudoku.copy()
                    cell.value = value
                    sudoku.deletePossibility(c, value)
                    if (solve(sudoku.copy(), nextCell(sudoku, c))) {
                        return true
                    }
                    sudoku = oldSudoku
                }
            }else if (solve(sudoku.copy(), nextCell(sudoku, c))) {
                return true
            }
            return false
        }
        return true
    }


    private fun nextCell(sudoku: Sudoku, c: Pair<Int, Int>): Pair<Int, Int> {
        var nextC: Pair<Int, Int>
        do {
            val x = (c.first + 1) % 9
            val y = if (x == 0) c.second + 1 else c.second
            nextC = Pair(x, y)
        } while (sudoku.getCell(nextC) is SCell)

        return nextC
    }
}

fun main(args: Array<String>) {
    val sudoku = Sudoku()
    println(sudoku.copy())
    println(Sudoku().toPrettyString())
}