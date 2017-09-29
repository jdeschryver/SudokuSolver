package solver

/**
 * @author Jan De Schryver <Jan.DeSchryver@bucephalus.be>*
 *
 */

const val ALL_BITS: Int = (1 shl 9) - 1

fun possibilities(vararg vectors: BitVector): MutableSet<Int> {
    var possVector = BitVector(ALL_BITS)
    val possibilities = mutableSetOf<Int>()

    vectors.forEach { possVector -= it }
    (0..8).forEach { if (possVector.isSet(it)) possibilities.add(it) }
    return possibilities
}

class BitVector(private var value: Int = 0) {

    private fun Int.toMask() = 1 shl (this - 1) // We start form 0, SudokuV1 from 1

    fun set(index: Int?) {
        index?.let { value = value or index.toMask() }
    }

    fun unSet(index: Int?) {
        index?.let { value = value and index.toMask() }
    }

    fun isSet(index: Int) = value and index.toMask() != 0

    operator fun minus(vector: BitVector) = BitVector(this.value and vector.value.inv())
}