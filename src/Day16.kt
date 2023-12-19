import kotlin.experimental.and
import kotlin.experimental.or
import kotlin.math.max


fun main() {
    fun printLayout(layout:List<CharArray>) {
        val l = layout[0].size
        for (y in 0..<layout.size) {
            for (x in 0..<l) {
                print(layout[y][x])
            }
            println()
        }
        println()
    }


    val N = Pair(-1,0)
    val E = Pair(0,1)
    val S = Pair(1,0)
    val W = Pair(0,-1)
    var layoutW = 0
    var layoutH = 0

    data class Light(
        val y:Int,
        val x:Int,
        val dir: Pair<Int, Int>
    )


    var result: Array<Array<Byte>> = Array(1){
        Array(1) { 0 }
    }

    fun Array<Array<Byte>>.printResult(s: Int) : Int {
        var cont=0
        println("$s)------")
        val l = this[0].size
        for (y in 0..<size) {
            for (x in 0..<l) {
                print("${this[y][x]},")
                cont+= if (this[y][x]>0) 1 else 0
            }
            println("")
        }
        println("Cont:$cont")
        return cont
    }

    fun setResult(c: Light) {
        //val b = result[y][x]
        val v: Byte = when (c.dir) {
            N -> 1
            S -> 2
            W -> 4
            E -> 8
            else -> 0
        }
        result[c.y][c.x] = result[c.y][c.x] or v
    }

    fun checkResult(c: Light): Boolean {
        val v: Byte = when (c.dir) {
            N -> 1
            S -> 2
            W -> 4
            E -> 8
            else -> 0
        }
        return (result[c.y][c.x] and v) > 0
    }


    fun MutableList<Light>.addLight(y:Int, x:Int, dir:Pair<Int,Int>) {
        if (x<0 || x>=layoutW || y<0 || y>=layoutH) {
            return
        }
        // si ya existe uno en la misma posicion y direccion, no agregar
        val c = Light(y,x,dir)
        if (checkResult(c)) {
            return
        }
        add(c)
    }


    fun enterLayout( layout: List<CharArray>, sy:Int, sx:Int, dir:Pair<Int,Int>) : Int {
        layoutW = layout.first().size
        layoutH = layout.size


        result = Array(layoutH) {
            Array<Byte>(layoutW) { 0 }
        }

        val stack = mutableListOf<Light>()
        stack.addLight(sy,sx, dir)

        while (stack.isNotEmpty()) {
            val c = stack.removeLast()
            //result.printResult(stack.size)

            setResult(c)
            when(layout[c.y][c.x]) {
                '.' ->  {
                    stack.addLight(c.y+c.dir.first,c.x+c.dir.second,c.dir)
                }
                '-' -> {
                    if (c.dir==W || c.dir==E) {
                        stack.addLight(c.y+c.dir.first,c.x+c.dir.second,c.dir)
                    } else {
                        stack.addLight(c.y,c.x-1,W)
                        stack.addLight(c.y,c.x+1,E)
                    }
                }
                '|' -> {
                    if (c.dir==N || c.dir==S) {
                        stack.addLight(c.y+c.dir.first,c.x+c.dir.second,c.dir)
                    } else {
                        stack.addLight(c.y-1,c.x,N)
                        stack.addLight(c.y+1,c.x,S)
                    }
                }
                '\\' -> {
                    val newDir = Pair(c.dir.second, c.dir.first)
                    stack.addLight(c.y+newDir.first,c.x+newDir.second,newDir)
                }
                '/' -> {
                    val newDir = Pair(-c.dir.second, -c.dir.first)
                    stack.addLight(c.y+newDir.first,c.x+newDir.second,newDir)
                }
            }
        }
        return result.printResult(0)
    }

    fun part1(input: List<String>): Int {
        val layout = input.map {
            it.toCharArray()
        }
        return enterLayout(layout,0, 0, E)
    }


    fun part2(input: List<String>): Int {
        val layout = input.map {
            it.toCharArray()
        }
        var tiles = 0

        for (y in 0..layout.size) {
            val te = enterLayout(layout, y,0,E)
            tiles = max(te, tiles)
            val tw = enterLayout(layout, y,layout[0].size-1,W)
            tiles = max(tw, tiles)
        }
        for (x in 0..layout[0].size) {
            val te = enterLayout(layout, 0,x,S)
            tiles = max(te, tiles)
            val tw = enterLayout(layout, layout.size-1,x,N)
            tiles = max(tw, tiles)
        }
        return tiles
    }

    val testInput = readInput("Day16_test")
    val i = part1(testInput)
    println("i=$i")
    check(i == 46)
    val j = part2(testInput)
    println("j=$j")
    check(j == 51)

    val input = readInput("Day16")
    part1(input).println()
    // 8389

    part2(input).println()
    //8564
}
