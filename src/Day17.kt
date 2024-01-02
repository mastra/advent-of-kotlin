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

    val MAX =  141*141*9

    class Heat(
        var y:Int,
        var x:Int,
        var heat:Int,
        var distance: Int = MAX,
        var added : Boolean = false,
        var prev: Heat? = null,
        var dir: Pair<Int, Int> = Pair(0,0),
        var dirTail :Int = 0,
    ) {
    }

    fun Array<Array<Heat>>.findMinDistance() : Heat? {
        var h : Heat? = null
        var m = MAX

        for (y in 0..<this.size) {
            for (x in 0..<this[0].size) {
                if (!this[y][x].added && this[y][x].distance < m ) {
                    h = this[y][x]
                    m = h.distance
                }
            }
        }
        return h
    }
    var layout : List<CharArray> = listOf(CharArray(10))

    fun markPath(city: Array<Array<Heat>>, y:Int, x:Int) {
        var current = city[y][x]
        while(current.prev!=null) {
            val dir = current.dir
            val c = when (dir) {
                Pair(0,1) -> '>'
                Pair(1,0) -> 'v'
                Pair(0,-1) -> '<'
                Pair(-1,0) -> '^'
                else -> '?'
            }
            print("${city[current.y][current.x].heat},")
            layout[current.y][current.x]=c
            current= current.prev!!
        }
        println()
    }

//    fun tail(current: Heat): Int {
//        var count=1
//        var h : Heat = current
//        val dir = h.dir()
//
//        while (count<4 && h.prev!=null) {
//            h = h.prev!!
//            var dir2= h.dir()
//            if (dir2!=dir) {
//                break;
//            }
//            count++
//        }
//        return count
//    }

    fun part1(input: List<String>): Int {
        layout = input.map {
            it.toCharArray()
        }
        layoutW = layout.first().size
        layoutH = layout.size

        val city = Array(layoutH) { y ->
            Array(layoutW) { x -> Heat(y,x, layout[y][x].code - '0'.code) }
        }

        city[0][0].distance = 0
        city[0][0].dir = E
        city[0][0].dirTail = 1

        while (true) {
                val h = city.findMinDistance()
                //if (h.y == layoutH-1 && h.x==layoutW-1) {
                if (h==null) {
                    println("END")
                    break;
                }
                h.added = true
//            layout = input.map {
//                it.toCharArray()
//            }
//            markPath(city, h.y, h.x)
//            println("-----")
//            printLayout(layout)
                println("CURRENT y:${h.y} x:${h.x} d:${h.distance} heat: ${h.heat} tail:${h.dirTail}")

                val p = h.prev
                //val t = tail(h)
                //
                listOf(N,E,S,W).map { dir ->

                    //if (t<=3 && h.dir()!=dir) {
                        val y0 = h.y + dir.first
                        val x0 = h.x + dir.second
                        if (!(x0 < 0 || x0 >= layoutW || y0 < 0 || y0 >= layoutH)) {
                            if (p == null || (x0 != p.x || y0 != p.y)) {
                                val next = city[y0][x0]
                                var tailCond = (dir!=h.dir ||  h.dirTail<3)
                                if (!next.added && (next.distance > h.distance + next.heat) && tailCond) {
                                    next.distance = h.distance + next.heat
                                    next.prev = h
                                    next.dir = dir

                                    if (dir==h.dir) {
                                        next.dirTail = h.dirTail + 1
                                    } else {
                                        next.dirTail = 1
                                    }
                                    println("     UPDATE y:${next.y}  x:${next.x} d:${next.distance} heat:${next.heat} dir: ${next.dir} tail: ${next.dirTail} tailCond:${tailCond}")
                                } else {
                                    println("     NO     y:${next.y}  x:${next.x} d:${next.distance} heat:${next.heat} dir: ${next.dir} tail: ${next.dirTail} tailCond:${tailCond}")
                                }
                            }
                        }
                   // }
                }
        }
        printLayout(layout)
        println("-----")
        markPath(city, layoutH-1, layoutW-1)
        println("-----")
        printLayout(layout)
        //val s = tail(city[4][layoutW-1])
        //city[0][0].heat +
        val result =  city[layoutH-1][layoutW-1].distance
        return result
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day17_test")
    val i = part1(testInput)
    println("i=$i")

    check(i == 102)
    //val j = part2(testInput)
    //println("j=$j")
    //check(j == 51)

    val input = readInput("Day17")
    part1(input).println()
    // 768 too low
    // 821 too high

    part2(input).println()
    //8564
}
