fun main() {
    fun part1(input: List<String>): Int {
        val regex = """[a-z]""".toRegex()
        var total = 0
        input.filter{ it!="" }.forEach {
            var number = regex.replace(it,"")
            number = number.first().toString() + number.last()
            //println("line $line i=$it n=$number int=${number.toInt()}")
            total+= number.toInt()
        }
        return total
    }

    fun part2(input: List<String>): Int {
        val output = mutableListOf<String>()

        input.forEach {
            var str = it
            listOf("one","two","three","four","five","six","seven","eight","nine").forEachIndexed { index, digit ->
                val digAndNum = "${digit.first()}${index+1}${digit.last()}"
                str = str.replace(digit, digAndNum)
            }
            //println("alt: $it re:$str")
            output.add(str)
        }

        return part1(output)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
