package ui

import api.Sudoku
import javafx.beans.property.*
import javafx.geometry.Pos
import javafx.scene.input.KeyCode
import javafx.scene.layout.GridPane
import solver.SudokuV1
import solver2.SudokuV2
import tornadofx.*
import kotlin.system.measureNanoTime

class SudokuUI2 : View() {

    private var backup: List<Int?> = ArrayList(9 * 9)

    private val solver = SimpleStringProperty()
    private val solving = SimpleBooleanProperty(false)

    private val sudokuEntries = hashMapOf<String, () -> Sudoku>(
            "J Solver" to ::SudokuV1,
            "K Solver" to ::SudokuV2
    )

    private val cells = Array(9 * 9) { index ->
        CellUI(index / 9, index % 9).apply {
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
    }

    override val root = borderpane {

        addClass(Styles.rootPane)

        bottom = hbox(5, Pos.CENTER) {
            prefHeight = 40.0
            prefWidth = 417.0
            disableWhen(solving)

            combobox(solver, sudokuEntries.keys.toList()) {
                prefHeight = 30.0
                prefWidth = 150.0
                selectionModel.selectFirst()
            }

            button("Solve") {
                prefHeight = 30.0
                prefWidth = 75.0
                action(this@SudokuUI2::solve)
            }

            button("Reset") {
                prefHeight = 30.0
                prefWidth = 75.0
                action {
                    backup.forEachIndexed { index, value ->
                        cells[index].value = value
                    }
                }
            }

            button("Clear") {
                prefHeight = 30.0
                prefWidth = 75.0
                action {
                    cells.forEach {
                        it.value = null
                        it.isEditable = true
                    }
                }
            }
        }

        center = gridpane {
            addClass(Styles.bigGrid)

            repeat(3) { outerRow ->
                repeat(3) { outerCol ->
                    val ch = GridPane().also {
                        it.addClass(Styles.grid)

                        repeat(3) { innerRow ->
                            repeat(3) { innerCol ->
                                val row = outerRow * 3 + innerRow
                                val col = outerCol * 3 + innerCol

                                val child = cells[row * 9 + col]

                                it.add(child, innerCol, innerRow)
                            }
                        }
                    }
                    add(ch, outerCol, outerRow)
                }
            }
        }
    }

    init {
        title = "Sudoku Solver"

        currentStage?.apply {
            isResizable = false
        }
    }

    private fun solve() {
        solving.value = true

        runAsync {
            backup = cells.map { it.value }
            val triples = cells.filter { it.value != null }.map { Triple(it.row, it.col, it.value!!) }

            val factory = sudokuEntries[solver.value]
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
            solving.value = false
        }
    }

    private fun selectCell(row: Int, col: Int) {
        val id = (9 * row + col) % cells.size
        cells[id].requestFocus()
    }

    // *** util ***

    private fun formatNano(nano: Long): String {
        val nanos = nano % 1000L
        val micros = (nano / 1_000L) % 1000L
        val millis = (nano / 1_000_000L) % 1000L
        val sec = nano / 1_000_000_000L
        return "${sec}s ${millis}ms ${micros}µs ${nanos}ns"
    }

}