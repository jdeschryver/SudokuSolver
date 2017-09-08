package ui

import javafx.scene.text.FontWeight
import tornadofx.*

class SudokuUI : View() {
    override val root = hbox {
        label("Hello world")
    }
}

class SudokuApp : App(SudokuUI::class, Styles::class)

class Styles : Stylesheet() {
    init {
        label {
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
            backgroundColor += c("#cecece")
        }
    }
}

