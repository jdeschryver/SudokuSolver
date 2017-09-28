package solver

import api.Cell
import api.ECell
import api.SCell
import api.Sudoku
import org.apache.commons.lang3.StringUtils

/**
 * @author Jan De Schryver <Jan.DeSchryver@bucephalus.be>
 */
class Sudoku: Sudoku() {

    private val rowPossibilities = Array(9) { BitVector() }
    private val colPossibilities = Array(9) { BitVector() }
    private val squarePossibilities = Array(9) { BitVector() }

    override fun fill(values: List<Triple<Int, Int, Int>>) {
        values.forEach { (row, col, value) ->
            this[row, col] = SCell(value)
        }
    }

    override fun solve(): Boolean {
        //TODO: Make this shit solve the fucking sudoku

        return true
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
