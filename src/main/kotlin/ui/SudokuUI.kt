package ui

import javafx.concurrent.Task
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane
import javafx.scene.layout.GridPane
import solver.Sudoku
import tornadofx.View
import tornadofx.addClass
import tornadofx.onChange

class SudokuUI : View() {
    override val root: BorderPane by fxml()

    private val grid: GridPane by fxid()
    private val g1: GridPane by fxid()
    private val g2: GridPane by fxid()
    private val g3: GridPane by fxid()
    private val g4: GridPane by fxid()
    private val g5: GridPane by fxid()
    private val g6: GridPane by fxid()
    private val g7: GridPane by fxid()
    private val g8: GridPane by fxid()
    private val g9: GridPane by fxid()

    private val solveButton: Button by fxid()
    private val resetButton: Button by fxid()

    private val grids = listOf(g1, g2, g3, g4, g5, g6, g7, g8, g9)

    private var currentSolve: Task<Sudoku>? = null

    init {
        title = "Sudoku Solver"

        currentStage?.apply {
            isResizable = false
        }

        grids.forEach { it.addClass(Styles.grid) }

    }


    private val cells = ArrayList<CellUI>(9 * 9)

    init {
        repeat(9) { row ->
            repeat(9) { col ->
                val child = CellUI(row, col)

                child.run {
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

                val gridId = 3 * (row / 3) + col / 3
                grids[gridId].add(child, col % 3, row % 3)
                cells.add(child)
            }
        }
    }

    private fun selectCell(row: Int, col: Int) {
        val id = (9 * row + col) % cells.size
        cells[id].requestFocus()
    }

    fun solve() {
        grid.isDisable = true
        solveButton.isDisable = true

        currentSolve = runAsync {
           Sudoku()
        } ui {
            grid.isDisable = false
            solveButton.isDisable = false
        }

        println("solve")
    }

    fun reset() {
        currentSolve?.cancel()
        cells.forEach {
            it.value = null
            it.isEditable = true
        }
        grid.isDisable = false
        solveButton.isDisable = false
        println("reset")
    }
}
