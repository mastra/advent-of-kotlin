


fun main() {

    fun part1(input: List<String>): Int {
        var total=0
        var current =0
        for (c in input[0]) {
            if (c==',' || c=='\n') {
                total+=current
                current=0
                continue
            }
            current+= c.code
            current*=17
            current%=256

        }
        total+=current
        return total
    }

    fun hashLabel(label: String) : Int {
        var current = 0
        label.forEach {
            current = ((current + it.code) * 17) % 256
        }
        return current
    }

    fun part2(input: List<String>): Int {
        var total = 0
        val operations = input[0].split(",")
        val boxes = Array(256) {
            mutableListOf<Pair<String,Int>>()
        }
        operations.forEach { op ->
            val posE = op.indexOf('=')
            val pos = if (posE>0) posE else op.indexOf('-')
            val boxLabel = op.substring(0,pos)
            val box = hashLabel(boxLabel)
            val i = boxes[box].indexOfFirst { p ->
                p.first == boxLabel
            }
            if (posE>0) {
                val lens = op[pos+1].digitToInt()
                if (i>=0) {
                    boxes[box][i] = Pair(boxLabel, lens)
                } else {
                    boxes[box].add(Pair(boxLabel, lens))
                }
            } else if (i>=0) {
                boxes[box].removeAt(i)
            }
        }
        boxes.forEachIndexed { box, lenses ->
                lenses.forEachIndexed { index, pair ->
                    total +=  (box + 1) * (index+1) * pair.second
            }
        }
        return total
    }

    val testInput = readInput("Day15_test")
    val i = part1(testInput)
    println("i=$i")
    check(i == 1320)
    val j = part2(testInput)
    println("j=$j")
    check(j == 145)

    val input = readInput("Day15")
    part1(input).println()
    // 517315

    part2(input).println()
    //247763
}
