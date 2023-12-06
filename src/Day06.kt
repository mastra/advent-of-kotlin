fun main() {

    fun part1(input: List<String>): Int {
        val regex  = " +".toRegex()
        val times = input.first().substring(10).trim().split(regex).map { it.toInt()}
        val record = input.get(1).substring(10).trim().split(regex).map { it.toInt()}
        var m = 1
        times.forEachIndexed { index, t ->
            var n=0
            for (i in 1..t/2) {
                val m = t-i
                val p = i * (t-i)
                val r = record.get(index)
                if ( i * (t-i) > record.get(index)) {
                    n++
                }
            }
            n = n*2
            if (t%2 ==0) {
                n--
            }
            m *=n
        }
        return m
    }

    fun part2(input: List<String>): Int {
        val t = input.first().substring(10).replace(" ","").toLong()
        val record = input.get(1).substring(10).replace(" ","").toLong()
        var n=0
        for (i in 1..t/2) {
            val m = t-i
            val p = i * (t-i)
            if ( i * (t-i) > record) {
                n++
            }
        }

        n = n*2
        if (t%2 ==0L) {
            n--
        }
        return n
    }

    val testInput = readInput("Day06_test")
    val i = part1(testInput)
    println("i=$i")
    check(i == 288)
    val j = part2(testInput)
    println("j=$j")
    check(j == 71503)

    val input = readInput("Day06")
    part1(input).println()
    // 1312850


    part2(input).println()
    //136096660
}
