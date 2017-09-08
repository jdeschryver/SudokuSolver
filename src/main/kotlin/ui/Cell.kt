package ui

import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.TextField
import tornadofx.onChange

class Cell(val row: Int, val col: Int) : TextField() {

    init {
        height = 44.0
        width = 44.0

        alignment = Pos.CENTER

        text = "$row $col"

        focusedProperty().onChange { newVal ->
            if (newVal) {
                selectAll()
            }
        }

        onMouseClicked = EventHandler {
            selectAll()
        }

        onKeyTyped = EventHandler {
            val c = it.character[0]
            when (c) {
                in '1'..'9' -> clear()
                else -> it.consume()
            }
        }

    }

}