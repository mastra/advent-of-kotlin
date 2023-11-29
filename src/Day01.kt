import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    fun part1(input: List<String>): Int {
        var elf = 1
        var maxCal = 0
        var elfCal = 0

        for (line in input) {
            if (line!="") {
                val cal = line.toInt()
                elfCal += cal
            } else {
                if (elfCal>maxCal)  {
                    maxCal = elfCal
                }
                elf++
                elfCal =0
            }
        }
        if (elfCal>0) {
            maxCal = maxOf(elfCal, maxCal)
        }
        println("elf: $elf")
        println("maxCal: $maxCal")
        return maxCal
    }

    fun part2(input: List<String>): Int {
        val calList = mutableListOf<Int>()
        var elf = 1
        var maxCal = 0
        var elfCal = 0

        for (line in input) {
            if (line!="") {
                val cal = line.toInt()
                elfCal += cal
            } else {
                if (elfCal>maxCal)  {
                    maxCal = elfCal
                }
                calList.add(elfCal)

                elf++
                elfCal =0
            }
        }
        if (elfCal>0) {
            calList.add(elfCal)
        }
        val newList = calList.sortedDescending()
        println("elf: $elf")
        println("maxCal: $maxCal")
        val sum = newList[0]+newList[1]+newList[2]
        println("Sum: $sum")
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = Path("src/Day01_test.txt").readLines()
    check(part1(testInput) == 10)
    part1(testInput).println()
    part2(testInput).println()

    val input = Path("src/Day01.txt").readLines()
    part1(input).println()
    part2(input).println()
}
