package ui

import javafx.event.EventHandler
import javafx.scene.control.TextField
import tornadofx.addClass
import tornadofx.onChange

class Cell(val row: Int, val col: Int) : TextField() {

    var value: Int? = null

    init {
        addClass(Styles.cell)

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
                in '1'..'9' -> {
                    clear()
                    value = c.toInt() - 48
                }
                else -> it.consume()
            }
        }

    }

}