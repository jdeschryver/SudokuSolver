import solver.Sudoku
import solver.editableCell

/**
 * @author Jan De Schryver <Jan.DeSchryver@bucephalus.be>
 */

class SudokuSolver {

    val sudoku = Sudoku()

    fun solve(c: Pair<Int, Int>): Boolean {
        if (c.second < 8) {
            val cell = sudoku.getCell(c)
            if(cell is editableCell) {
                for (value in cell.possibilities) {
                    cell.value = value
                    sudoku.deletePossibility(c, value)
                    if (solve(nextCell(c))) {
                        return true
                    }
                }
            }else if (solve(nextCell(c))) {
                return true
            }
            return false
        }
        return true
    }


    private fun nextCell(c: Pair<Int, Int>): Pair<Int, Int> {
        var nextC: Pair<Int, Int>
        do {
            val x = (c.first + 1) % 9
            val y = if (x == 0) c.second + 1 else c.second
            nextC = Pair(x, y)
        } while (sudoku.getCell(nextC) is editableCell)

        return nextC
    }
}

fun main(args: Array<String>) {
    val sudoku = Sudoku()
    println(Sudoku().toPrettyString())
}