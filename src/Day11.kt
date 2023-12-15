import kotlin.math.abs

typealias Pos = Pair<Int, Int>
typealias PosLong = Pair<Long, Long>

fun main() {
//..F7.
//.FJ|.
//SJ.L7
//|F--J
//LJ...

    fun distance(g1:Pos, g2:Pos) : Int {
        val dy = abs(g1.first-g2.first)
        val dx = abs(g1.second-g2.second)
        return ( dy + dx)
    }

    fun distanceLong(g1:PosLong, g2:PosLong) : Long {
        val dy = abs(g1.first-g2.first)
        val dx = abs(g1.second-g2.second)
        return ( dy + dx)
    }

    fun part1(input: List<String>): Int {
        var galaxies = mutableListOf<Pos>()
        val w = input.first().length
        var y =0
        var columns = Array(w){ '.' }

        input.forEachIndexed { index, s ->
            for (j in 0..<w) {
                if (s[j]=='#') {
                    galaxies.add(Pair(y,j))
                    columns[j]='#'
                }
            }
            val g = s.map { c ->
                if (c=='#') 1 else 0
            }.sum()
            if (g==0) {
                y++
            }
            y++
        }
        var x =0
        for (i in 0..<w) {
            if (columns[i]=='.') {
                //
                for (j in 0..<galaxies.size) {
                    if (galaxies[j].second>x) {
                        galaxies[j] = Pair(galaxies[j].first, galaxies[j].second+ 1)
                    }
                }
                x+= (2 - 1)
            }
            x++
        }


        val l = galaxies.size
        var total =0
        var t = 0
        for (i in 0..<l) {
            for (j in i..<l) {
                total += distance(galaxies[i], galaxies[j])
                t++
            }
        }
        return total
    }

    fun printGalaxies(input: List<String>) {
        input.forEachIndexed { index, s ->
            for (j in 0..<s.length) {
                if (s[j]=='#') {
                    println("index:$index $index, $j")
                }
                }
            }
    }

    fun part2(input: List<String>, jump: Int): Long {
        var galaxies = mutableListOf<PosLong>()
        val w = input.first().length
        var y = 0L
        var columns = Array(w){ '.' }
        printGalaxies(input)

        println("-----------")
        input.forEachIndexed { index, s ->
            for (j in 0..<w) {
                if (s[j]=='#') {
                    galaxies.add(PosLong(y,j.toLong()))
                    columns[j]='#'
                }
            }
            val g = s.map { c ->
                if (c=='#') 1 else 0
            }.sum()
            if (g==0) {
                y+=jump
            } else {
                y++
            }
        }
        var x = 0L
        for (i in 0..<w) {

            if (columns[i]=='.') {
                //
                for (j in 0..<galaxies.size) {
                    if (galaxies[j].second>x) {
                        galaxies[j] = Pair(galaxies[j].first, galaxies[j].second+ (jump-1))
                    }
                }
                x+=(jump-1)
            }
            x++
        }

        val l = galaxies.size
        var total =0L
        var t = 0
        for (i in 0..<l) {
            for (j in i+1..<l) {
                val d = distanceLong(galaxies[i], galaxies[j])
                total += d
                println("i:$i j:$j d:d$d total:$total")
                t++
            }
        }
        return total
    }

    val testInput = readInput("Day11_test")
    val i = part1(testInput)
    println("i=$i")
    check(i == 374)
    val j = part2(testInput,100)
    println("j=$j")
    check(j == 8410L)

    val input = readInput("Day11")
    part1(input).println()
    // 9370588

    part2(input, 1000000).println()
    //746207878188
}
