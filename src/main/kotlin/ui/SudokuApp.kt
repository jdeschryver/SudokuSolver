package ui

import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class SudokuApp : App(SudokuUI::class, Styles::class)

class Styles : Stylesheet() {

    companion object {
        val cell by cssclass()
        val grid by cssclass()
    }

    init {
        cell {
            prefHeight = 44.px
            prefWidth = 44.px

            alignment = Pos.CENTER

            backgroundColor = multi(c("white", 0.0))

            fontSize = 20.px
            fontWeight = FontWeight.BOLD
        }

        grid {
            borderColor = multi(box(Color.BLACK))
            gridLinesVisible = true
            borderColor = multi(box(Color.BLACK))
            borderWidth = multi(box(2.px))
        }

    }
}
