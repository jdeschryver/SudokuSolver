import solver.Sudoku

/**
 * @author Jan De Schryver <Jan.DeSchryver@bucephalus.be>
 */

class SudokuSolver{

    val sudoku = Sudoku()

    fun solve(){

    }

    fun deletePossibility(c: Pair<Int, Int>, value: Int){
        val blockX = Math.floor(c.first / 3.0).toInt() * 3
        val blockY = Math.floor(c.second / 3.0).toInt() * 3

        repeat(9) { x ->
            val range = controlLoop(c, x, blockX, blockY)
            for(y in range.first..range.second){

            }
        }
    }

    fun controlLoop(c: Pair<Int, Int>, x: Int, blockX: Int, blockY: Int): Pair<Int, Int> = when(x){
        c.first -> Pair(0,8)
        in blockX..blockX+3 -> Pair(blockY, blockY+3)
        else -> Pair(c.second, c.second)
    }
}

fun main(args: Array<String>) {
    println("Hello, world!")
    println(Sudoku().toPrettyString())
}