package ui

import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class SudokuApp2 : App(SudokuUI::class, Styles::class)
class SudokuApp : App(SudokuUI2::class, Styles::class)

class Styles : Stylesheet() {

    companion object {
        val rootPane by cssclass()
        val cellUI by cssclass()
        val bigGrid by cssclass()
        val grid by cssclass()
    }

    init {
        rootPane {
            prefHeight = 442.px
            prefWidth = 405.px
        }

        cellUI {
            prefHeight = 44.px
            prefWidth = 44.px

            alignment = Pos.CENTER

            backgroundColor = multi(c("white", 0.0))

            fontSize = 20.px
            fontWeight = FontWeight.BOLD
        }

        bigGrid {
            prefHeight = 405.px
            prefWidth = 405.px

            alignment = Pos.CENTER
        }

        grid {
            prefHeight = 135.px
            prefWidth = 135.px

            borderColor = multi(box(Color.BLACK))
            gridLinesVisible = true
            borderWidth = multi(box(2.px))
        }

    }
}
