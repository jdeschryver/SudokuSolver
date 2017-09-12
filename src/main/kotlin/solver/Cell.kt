package solver

sealed class Cell {
    abstract val value: Int
    abstract fun copy(): Cell
}

class SCell(override val value: Int) : Cell() {

    // No operations are executed on a static cell, copy returns same object
    override fun copy(): Cell {
        return this
    }
}

class ECell(override var value: Int = -1,
            val possibilities: MutableList<Int> = (1..9).toMutableList()) : Cell() {

    override fun copy(): Cell {
        val possibilities = mutableListOf<Int>()
        possibilities.forEach { it -> possibilities.add(it) }
        return ECell(value, possibilities)
    }

    override fun toString(): String {
        return "$value, $possibilities "
    }
}