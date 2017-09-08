package ui

import javafx.scene.paint.Color
import tornadofx.*

class SudokuApp : App(SudokuUI::class, Styles::class)

class Styles : Stylesheet() {

    init {
        datagrid {
            gridLinesVisible = true
            borderColor = multi(box(Color.BLACK))
            borderWidth = multi(box(2.px))

        }
    }
}
