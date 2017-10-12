package ui

import javafx.event.*
import javafx.scene.control.TextField
import javafx.scene.input.ContextMenuEvent
import tornadofx.*

class CellUI(val row: Int, val col: Int) : TextField() {

    var value: Int?
        get () = if (text.isEmpty()) null else text[0].toInt() - 48
        set(v) = if (v in 1..9) text = v.toString() else clear()

    init {
        addClass(Styles.cellUI)

        addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume)

        focusedProperty().onChange { if (it) selectAll() }
        setOnMouseClicked { selectAll() }

        setOnKeyTyped {
            val c = it.character[0]
            when (c) {
                in '1'..'9' -> clear()
                else -> it.consume()
            }
        }

    }

}