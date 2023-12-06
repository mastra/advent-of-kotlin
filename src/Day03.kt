import kotlin.math.max
import kotlin.math.min

fun main() {

    fun findAround(input: List<String>, index: Int, number: String, pos:Int): Int {
        val rg="""[^\d^.]""".toRegex()
        for (i in max(0, index-1)..min(input.size-1, index+1)) {
            val line = input[i]
            val portion = line.substring(max(pos - 1, 0), min(pos + number.length + 1, line.length))
            //println("portion = $portion")
            if (rg.find(portion) != null) {
                return number.toInt()
            }
        }
        return 0
    }

    // debe ser una lista?
    fun findGearAround(input: List<String>, index: Int, number: String, pos:Int): Pair<Int,Int>? {
        val rg="""\*""".toRegex()
        for (i in max(0, index-1)..min(input.size-1, index+1)) {
            val line = input[i]
            val start = max(pos - 1, 0)
            val portion = line.substring(start, min(pos + number.length + 1, line.length))
            //println("portion = $portion")
            rg.findAll(portion).forEach {
                return Pair(i, it.range.start + start)
            }
        }
        return null
    }

    fun part1(input: List<String>): Int {
        var total = 0
        input.forEachIndexed { index, s ->
            val rg="""[^0-9]""".toRegex()
            val numbers = s.split(rg)
            //println("s:$s")
            numbers.filter { number -> number!="" }
                .toMutableSet()
                .forEach { number ->
                        val pattern ="""\b$number\b"""
                        Regex(pattern).findAll(s).forEach { match ->
                            val pos = match.range.start
                            val r = findAround(input, index, number, pos)
                            total += r
                        }

                }
        }
        return total
    }

    fun part2(input: List<String>): Int {
        var gears = mutableMapOf<Pair<Int, Int>, MutableList<Int>>( )
        input.forEachIndexed { index, s ->
            val rg="""[^0-9]""".toRegex()
            val numbers = s.split(rg)
            //println("s:$s")
            numbers.filter { number -> number!="" }
                .toMutableSet()
                .forEach { number ->
                    val pattern ="""\b$number\b"""
                    Regex(pattern).findAll(s).forEach { match ->
                        val pos = match.range.start
                        val gear = findGearAround(input, index, number, pos)
                        if (gear!=null) {
                            if (gears.get(gear)==null) {
                                gears[gear] = mutableListOf(number.toInt())
                            } else {
                                gears[gear]?.add(number.toInt())
                            }
                        }
                    }

                }
        }
        var total = 0
        gears.forEach { t, u ->
            if (u.size == 2) {
                val m = u[0]*u[1]
                total += m
            }
        }
        return total
    }

    // test if implementation meets criteria from the description, like:
    check(part1(readInput("Day03_test2"))==90)

    val testInput = readInput("Day03_test3")
    val i = part1(testInput)
    println("i=$i")
    check(i == 413)
    //check(i == 4361)

    val part2input = readInput("Day03_part2")
    check(part2(part2input) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    //535078

    part2(input).println()
    //75312571
}
