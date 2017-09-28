package solver

/**
 * @author Jan De Schryver <Jan.DeSchryver@bucephalus.be>*
 *
 */

class BitVector(private var value: Int = 0) {

    fun Int.toMask() = 1 shl this

    fun set(index: Int){
        value = value or index.toMask()
    }

    fun unSet(index: Int){
        value = value and index.toMask().inv()
    }

    fun isSet(index: Int) = value and index.toMask() != 0

    override fun toString() =  "$value"
}