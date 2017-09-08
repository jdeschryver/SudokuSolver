package ui

import javafx.scene.layout.BorderPane
import javafx.scene.layout.GridPane
import tornadofx.View

class SudokuUI : View() {
    override val root: BorderPane by fxml()
    val grid: GridPane by fxml()

    init {
        title = "Sudoku Solver"

        currentStage?.apply {
            isResizable = false
        }
    }

    fun solve(){
        println("solve")
    }

    fun reset(){
        println("reset")
    }
}
