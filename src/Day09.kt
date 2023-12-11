fun main() {
//0 3 6 9 12 15
//1 3 6 10 15 21
//10 13 16 21 30 45
    fun part1(input: List<String>): Long {
        val history = mutableListOf<List<Long>>()

        input.forEach { l ->
            val h = l.split(" ").map { it.toLong() }
            history.add(h)
        }
        var total = 0L

        history.forEach { h ->

            val diff = mutableListOf<List<Long>>()
            var current = h
            do {
                val prono = mutableListOf<Long>()
                diff.add(prono)
                for (i in 0..<current.size-1) {
                    val r = current[i+1]-current[i]
                    prono.add(r)
                }
                current = prono

            } while (current.filter { it==0L }.count() != current.size)


            diff.reversed().forEach {
                total += it.last()
            }
            total+= h.last()
        }
        return total
    }

    fun part2(input: List<String>): Long {
        val history = mutableListOf<List<Long>>()

        input.forEach { l ->
            val h = l.split(" ").map { it.toLong() }
            history.add(h)
        }
        var total = 0L

        history.forEach { h ->

            val diff = mutableListOf<List<Long>>()
            var current = h
            do {
                val prono = mutableListOf<Long>()
                diff.add(prono)
                for (i in 0..<current.size-1) {
                    val r = current[i+1]-current[i]
                    prono.add(r)
                }
                current = prono

            } while (current.filter { it==0L }.count() != current.size)

            var s = 0L
            diff.reversed().forEach {
                s = it.first() - s
            }
            total += (h.first() - s)
        }
        return total
    }

    val testInput = readInput("Day09_test")
    val i = part1(testInput)
    println("i=$i")
    check(i == 114L)
    val j = part2(testInput)
    println("j=$j")
    check(j == 2L)

    val input = readInput("Day09")
    part1(input).println()
    // 1970607474

    part2(input).println()
    // 8245452805243
}
