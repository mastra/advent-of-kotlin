


fun main() {


    fun findReflectHoriz(pattern: List<String>) : Int {
        for (i in 0..<pattern.size-1) {
            if (pattern[i]==pattern[i+1]) {
                var j = i
                var h = i + 1
                while (pattern[j] == pattern[h]) {
                    j--
                    h++
                    if (j < 0 || h >= pattern.size) {
                        return i + 1
                    }
                }
            }
        }
        return 0
    }

    fun findReflectVert(pattern: List<String>): Int {
        val result = Array(pattern.first().length) {
            ""
        }
        pattern.forEach {  line ->
            line.forEachIndexed { index, c ->
                result[index] = result[index] + c
            }
        }
        return findReflectHoriz(result.toList())
    }

    fun part1(input: List<String>): Int {
        var total=0

        var patterns = mutableListOf<List<String>>()
        var current = mutableListOf<String>()
        input.forEach {
            if (it=="") {
                patterns.add(current)
                current = mutableListOf<String>()
            } else {
                current.add(it)
            }
        }
        if (current.size>0) {
            patterns.add(current)
        }
        println("pattern ${patterns.size}")
//        patterns.chunked(2).forEach { (l1,l2) ->
//            val v = findReflectVert(l1)
//            val h = findReflectHoriz(l2)
//            total += (v+(h*100))
//        }
        patterns.forEach { l ->
            val v = findReflectVert(l)
            val h = findReflectHoriz(l)
            if (v>h) {
                total+=v
            } else {
                total+= (h*100)
            }
            //total += (v+(h*100))
        }
        return total
    }


    fun part2(input: List<String>, jump: Int): Long {
        var total = 0L
        return total
    }

    val testInput = readInput("Day13_test")
    val i = part1(testInput)
    println("i=$i")
    check(i == 405)
//    val j = part2(testInput,100)
//    println("j=$j")
//    check(j == 8410L)

    val input = readInput("Day13")
    part1(input).println()
    //27202

  //  part2(input, 1000000).println()
    //746207878188
}
