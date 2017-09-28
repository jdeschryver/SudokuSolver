package api

sealed class Cell {
    abstract val value: Int?
}

class SCell(override val value: Int) : Cell()
class ECell(override var value: Int? = null) : Cell()
