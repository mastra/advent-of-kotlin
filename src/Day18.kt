fun main() {
    var total = 0

    val U = Pair(-1,0)
    val R = Pair(0,1)
    val D = Pair(1,0)
    val L = Pair(0,-1)

    fun MutableMap<Int, MutableMap<Int, Boolean>>.isHole(y:Int, x:Int): Boolean {
        return this.get(y)?.get(x) == true
    }
    fun MutableMap<Int, MutableMap<Int, Boolean>>.digHole(y:Int, x:Int) {
        if (get(y) == null ) {
            this.put(y, mutableMapOf(Pair(x, true)))
        } else {
            this.get(y)?.set(x, true)
        }
    }


    fun MutableMap<Int, MutableMap<Int, Boolean>>.fill(sy:Int, sx:Int) : Int {
        var count = 0
        val toFill = mutableListOf<Pair<Int,Int>>()
        if (isHole(sy,sx)) {
            return 0
        }
        toFill.add(Pair(sy,sx))
        while (toFill.isNotEmpty()) {
            val (y, x ) = toFill.removeAt(0)
            if (isHole(y,x))
                continue;
            digHole(y,x)
            count++
            listOf(U, R, D, L).forEach {
                var y0 = y + it.first
                val x0 = x + it.second
                //if (x0 >= 0 && x <= dim.second && y0 >= 0 && y0 <= dim.first) {
                    if (!isHole(y0,x0)) {
                        toFill.add(Pair(y0, x0))
                    }
                //}
            }
        }
        return count
    }

    fun MutableMap<Int, MutableMap<Int, Boolean>>.print(): Triple<Int,Int, Int> {
        val hmin = this.keys.min()
        val h = this.keys.max()

        var w = 0
        this.forEach { (y, map) ->
            val x = map.keys.max()
            if (x>w) {
                w=x
            }
        }

        for (y in hmin..h) {
            for (x in 0..w) {
                val c = if (isHole(y,x)) '#' else '.'
                print(c)
            }
            println("")
        }
        println(" H=$h W=$w")
        return Triple(hmin,h,w)
    }

    fun MutableMap<Int, MutableMap<Int, Boolean>>.fillByLine(dim: Triple<Int,Int, Int>):Int {
        val hmin = dim.first
        val h = dim.second
        val w = dim.third
        var holes = 0

        for (y in hmin..h) {
            var inside = 0
            var x = 0
            while (x <=w) {
                if (isHole(y,x) && !isHole(y,x-1)) {
                    inside++
                }
                if (!isHole(y,x) && inside % 2 ==1) {
                    holes++
                }
                x++
            }
        }
        return holes
    }

    data class Hole(
        var y:Int,
        var x:Int,
    )

    fun part1(input: List<String>): Int {
        var x = 0
        var y = 0
        val sparseArray = mutableMapOf<Int, MutableMap<Int, Boolean>>()
        total = 1
        sparseArray.digHole(0,0)
        input.forEach {
            val (dir, stepStr, color) = it.split(" ")
            val inc = when (dir) {
                "U" -> U
                "D" -> D
                "R" -> R
                "L" -> L
                else -> Pair(0,0)
            }
            val steps = stepStr.toInt()
            for (i in 0..<steps) {
                y += inc.first
                x += inc.second
                if (!sparseArray.isHole(y,x)) {
                    total++
                    sparseArray.digHole(y,x)
                }
            }
        }
        val dim = sparseArray.print()
        total += sparseArray.fill(1,1)
        sparseArray.print()
        return total
    }

    fun part2(input: List<String>): Int {
        var x = 0
        var y = 0
        val sparseArray = mutableMapOf<Int, MutableMap<Int, Boolean>>()
        total = 1
        sparseArray.digHole(0,0)
        input.forEach {
            val (dir, stepStr, color) = it.split(" ")
            val inc = when (dir) {
                "U" -> U
                "D" -> D
                "R" -> R
                "L" -> L
                else -> Pair(0,0)
            }
            val steps = stepStr.toInt()
            for (i in 0..<steps) {
                y += inc.first
                x += inc.second
                if (!sparseArray.isHole(y,x)) {
                    total++
                    sparseArray.digHole(y,x)
                }
            }
        }
        val dim = sparseArray.print()
        total += sparseArray.fillByLine(dim)
        sparseArray.print()
        return total
    }

    val testInput = readInput("Day18_test")
    val i = part1(testInput)
    println("i=$i")

    check(i == 62)
    println("Part 2")
    val j = part2(testInput)
    //println("j=$j")
    check(j == 62)

    val input = readInput("Day18")
    part1(input).println()
    // 55677 too low
    //61661
    println("part2")
    part2(input).println()
}
