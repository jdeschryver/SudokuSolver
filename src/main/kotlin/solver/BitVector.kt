package solver

/**
 * @author Jan De Schryver <Jan.DeSchryver@bucephalus.be>*
 *
 */

fun bitsOf(vararg bits: Int) : BitVector {
    return BitVector().apply { bits.forEach{ set(it) } }
}

class BitVector(private var value: Int = 0) {

    fun set(index: Int){
        value = value or (1 shl index)
    }

    fun unSet(index: Int){
        value = value and (1 shl index).inv()
    }

    fun isSet(index: Int) = (value and (1 shl index)) != 0

    infix fun combine(vector: BitVector) =  BitVector(vector.value or this.value)
    infix fun intersect(vector: BitVector) =  BitVector(vector.value and this.value)

    override fun toString() =  "$value"
}