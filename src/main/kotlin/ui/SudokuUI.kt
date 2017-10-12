package ui

import api.Sudoku
import javafx.scene.control.*
import javafx.scene.input.KeyCode
import javafx.scene.layout.*
import solver.SudokuV1
import solver2.SudokuV2
import tornadofx.*
import kotlin.system.measureNanoTime

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

    private val comboBox: ComboBox<String> by fxid()

    private val solveButton: Button by fxid()
    private val resetButton: Button by fxid()
    private val clearButton: Button by fxid()

    private val grids = listOf(g1, g2, g3, g4, g5, g6, g7, g8, g9)

    private val cells = ArrayList<CellUI>(9 * 9)
    private var backup: List<Int?> = ArrayList(9 * 9)

    private val sudokuEntries = hashMapOf<String, () -> Sudoku>(
            "J Solver" to ::SudokuV1,
            "K Solver" to ::SudokuV2
    )

    init {
        title = "Sudoku Solver"

        solveStatus(false)

        currentStage?.apply {
            isResizable = false
        }

        comboBox.items.addAll(sudokuEntries.keys)
        comboBox.value = sudokuEntries.keys.first()

        grids.forEach { it.addClass(Styles.grid) }

        repeat(9) { row ->
            repeat(9) { col ->
                val child = CellUI(row, col).apply {
                    textProperty().onChange {
                        if (!text.isEmpty())
                            selectCell(row, col + 1)
                    }

                    setOnKeyPressed {
                        when (it.code) {
                            KeyCode.BACK_SPACE -> {
                                clear()
                                selectCell(row + 8, col + 8)
                            }
                            KeyCode.DELETE -> clear()
                            KeyCode.ENTER, KeyCode.RIGHT, KeyCode.TAB -> selectCell(row, col + 1)
                            KeyCode.LEFT -> selectCell(row + 8, col + 8)
                            KeyCode.UP -> selectCell(row + 8, col)
                            KeyCode.DOWN -> selectCell(row + 1, col)
                            else -> {
                            }
                        }
                        it.consume()
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

    private fun solveStatus(solving: Boolean) {
        grid.isDisable = solving
        comboBox.isDisable = solving
        solveButton.isDisable = solving
        resetButton.isDisable = solving
        clearButton.isDisable = solving

    }

    private fun formatNano(nano: Long): String {
        val nanos = nano % 1000L
        val micros = (nano / 1_000L) % 1000L
        val millis = (nano / 1_000_000L) % 1000L
        val sec = nano / 1_000_000_000L
        return "${sec}s ${millis}ms ${micros}µs ${nanos}ns"
    }

    fun solve() {
        solveStatus(true)

        runAsync {
            backup = cells.map { it.value }
            val triples = cells.filter { it.value != null }.map { Triple(it.row, it.col, it.value!!) }

            val factory = sudokuEntries[comboBox.value]
            when (factory) {
                null -> false to null
                else -> {
                    val sudoku: Sudoku = factory.invoke()

                    sudoku.fill(triples)
                    var solved = false
                    val nano = measureNanoTime {
                        solved = sudoku.solve()
                    }
                    println("solved in ${formatNano(nano)}.")
                    solved to sudoku
                }
            }

        } ui { (solved, sudoku) ->
            when {
                sudoku == null -> warning("Specified solver could not be found.")
                solved -> sudoku.toArray().forEachIndexed { index, value ->
                    cells[index].value = value
                }
                else -> warning("Sudoku cannot be solved.")
            }
            solveStatus(false)
        }
    }

    fun reset() {
        backup.forEachIndexed { index, value ->
            cells[index].value = value
        }
    }

    fun clear() {
        cells.forEach {
            it.value = null
            it.isEditable = true
        }
        solveStatus(false)
    }
}
