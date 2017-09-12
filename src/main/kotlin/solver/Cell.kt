package solver

sealed class Cell {
    abstract val value: Int
    abstract fun copy(): Cell
}

class SCell(override val value: Int) : Cell() {

    // No operations are executed on a static cell, copy returns same object
    override fun copy() = this
}

class ECell(override var value: Int = -1,
            val possibilities: MutableList<Int> = (1..9).toMutableList()) : Cell() {

    override fun copy() = ECell(value, ArrayList(possibilities))

    override fun toString() = "$value, $possibilities"

}