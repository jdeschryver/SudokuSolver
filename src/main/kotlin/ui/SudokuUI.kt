package ui

import javafx.event.EventHandler
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane
import javafx.scene.layout.GridPane
import tornadofx.View
import tornadofx.onChange

class SudokuUI : View() {
    override val root: BorderPane by fxml()

    private val grid: GridPane by fxid()

    private val cells = arrayListOf<Cell>()

    init {
        title = "Sudoku Solver"

        currentStage?.apply {
            isResizable = false
        }

        repeat(9) { i ->
            repeat(9) { j ->
                val child = Cell(i, j)

                with(child) {
                    textProperty().onChange {
                        selectCell(row, col+1)
                    }

                    onKeyPressed = EventHandler {
                        when (it.code) {
                            KeyCode.ENTER, KeyCode.RIGHT -> selectCell(row, col+1)
                            KeyCode.LEFT -> selectCell(row+8, col+8)
                            KeyCode.UP -> selectCell(row+8, col)
                            KeyCode.DOWN -> selectCell(row+1, col)
                            else -> it.consume()
                        }
                    }
                }

                grid.add(child, j, i)
                cells.add(child)
            }
        }
    }

    private fun selectCell(row: Int, col: Int){
        val id = (9*row + col) % cells.size
        cells[id].requestFocus()
    }


    fun solve() {
        println("solve")
    }

    fun reset() {
        println("reset")
    }
}
