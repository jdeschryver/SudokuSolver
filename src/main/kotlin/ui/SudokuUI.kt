package ui

import javafx.event.EventHandler
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane
import javafx.scene.layout.GridPane
import tornadofx.View
import tornadofx.addClass
import tornadofx.onChange

class SudokuUI : View() {
    override val root: BorderPane by fxml()
    private val g1: GridPane by fxid()
    private val g2: GridPane by fxid()
    private val g3: GridPane by fxid()
    private val g4: GridPane by fxid()
    private val g5: GridPane by fxid()
    private val g6: GridPane by fxid()
    private val g7: GridPane by fxid()
    private val g8: GridPane by fxid()
    private val g9: GridPane by fxid()

    private val grids = listOf(g1, g2, g3, g4, g5, g6, g7, g8, g9)

    init {
        title = "Sudoku Solver"

        currentStage?.apply {
            isResizable = false
        }

        grids.forEach { it.addClass(Styles.grid) }

    }


    private val cells = arrayListOf<CellUI>()


    init {
        repeat(9) { i ->
            repeat(9) { j ->
                val child = CellUI(i, j)

                with(child) {
                    textProperty().onChange {
                        if (!text.isEmpty())
                            selectCell(row, col + 1)
                    }

                    onKeyPressed = EventHandler {
                        when (it.code) {
                            KeyCode.BACK_SPACE -> {
                                clear()
                                selectCell(row + 8, col + 8)
                            }
                            KeyCode.DELETE -> clear()
                            KeyCode.ENTER, KeyCode.RIGHT -> selectCell(row, col + 1)
                            KeyCode.LEFT -> selectCell(row + 8, col + 8)
                            KeyCode.UP -> selectCell(row + 8, col)
                            KeyCode.DOWN -> selectCell(row + 1, col)
                            else -> it.consume()
                        }
                    }
                }

                val gridId = 3 * (i / 3) + j / 3
                grids[gridId].add(child, j % 3, i % 3)
                cells.add(child)
            }
        }
    }

    private fun selectCell(row: Int, col: Int) {
        val id = (9 * row + col) % cells.size
        cells[id].requestFocus()
    }

    fun solve() {
        println("solve")
    }

    fun reset() {
        println("reset")
    }
}
