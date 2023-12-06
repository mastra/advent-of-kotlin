import kotlin.math.min
import kotlin.math.pow

fun main() {

    fun calcPoint(win: List<String>, have:List<String>) : Int {
        val s = have.intersect(win).size
        if (s<2) {
            return s
        }
        return (2.0.pow(s-1)).toInt()
    }

    fun matchingNumbers(win: List<String>, have:List<String>) : Int {
        return have.intersect(win).size
    }

    fun part1(input: List<String>): Int {
        val regex  = " +".toRegex()
        return  input.map { line ->
            val (winning, having) = line.split(":")[1].split("|")
            calcPoint(winning.trim().split(regex), having.trim().split(regex))
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val pile = Array<Int>(input.size) { 1 }
        val regex  = " +".toRegex()
        input.forEachIndexed { index, line ->
            val (winning, having) = line.split(":")[1].split("|")
            val n = matchingNumbers(winning.trim().split(regex), having.trim().split(regex))
            if (n>0) {
                for (i in min(index + 1, pile.size - 1)..min(index + n, pile.size - 1)) {
                    pile[i] += pile[index]
                }
            }
        }
        return pile.sum()
    }

    val testInput = readInput("Day04_test")
    val i = part1(testInput)
    println("i=$i")
    check(i == 13)
    val j = part2(testInput)
    check(j == 30)

    val input = readInput("Day04")
    part1(input).println()
    // 25174


    part2(input).println()
    //6420979
}
