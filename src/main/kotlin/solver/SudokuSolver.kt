import solver.Sudoku
import solver.ECell

/**
 * @author Jan De Schryver <Jan.DeSchryver@bucephalus.be>
 */

class SudokuSolver {

    fun solve(sudoku: Sudoku, c: Pair<Int, Int>): Boolean {
        if (c.second < 8) {
            val cell = sudoku.getCell(c)
            if(cell is ECell) {
                for (value in cell.possibilities) {
                    cell.value = value
                    sudoku.deletePossibility(c, value)
                    if (solve(sudoku.copy(), nextCell(sudoku, c))) {
                        return true
                    }
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
        } while (sudoku.getCell(nextC) is ECell)

        return nextC
    }
}

fun main(args: Array<String>) {
    val sudoku = Sudoku()
    println(sudoku.copy())
    println(Sudoku().toPrettyString())
}