fun main() {

    class Part(val catMap: Map<Char, Int>) {
    }
    class Rule(
        val action:String,
        val cat: Char? = null,
        val op: Char? = null,
        val v: Int = 0,
    ) {
        fun condition( p: Part) : Boolean {
            if (cat==null) return true
            if (op=='>') {
                return p.catMap[cat]!! > v
            }
            return p.catMap[cat]!! < v
        }
    }
    class WorkFlow(val name:String, val rules: List<Rule>) {
    }


    fun part1(input: List<String>): Int {
        var total = 0

        val j = input.indexOf("")
        val cmdInput = input.subList(0, j)
        val partInput = input.subList(j+1, input.size)
        val workFlow = cmdInput.map {
            val i = it.indexOf('{')
            val name = it.substring(0, i)
            val rules = it.substring(i + 1, it.length - 1).split(',').map {
                val p = it.split(':')
                if (p.size == 1) {
                    Rule(p[0])
                } else {
                    Rule(p[1], p[0][0], p[0][1], p[0].substring(2).toInt())
                }
            }
            Pair(name, rules)
        }.toMap()

        val parts = partInput.map {
            //val map = mutableMapOf<Char,Int>()
            val map = it.substring(1, it.length-1).split(',').map {
                Pair(it[0], it.substring(2).toInt())
            }.toMap()
            Part(map)
        }

        parts.forEach { part ->
            var w = "in"
            while (w!="A" && w!="R") {
                val wf = workFlow[w]!!
                var action = ""
                for (i in 0..<wf.size) {
                    val rule = wf[i]
                    if (rule.condition(part)==true) {
                        action = rule.action
                        break
                    }
                }
                if (action=="") {
                    w = wf.last().action
                } else {
                    w = action
                }
            }
            if (w=="A") {
                val s = part.catMap.values.sum()
                total +=s
            }
        }
        println(" w: ")


        return total
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day19_test")
    val i = part1(testInput)
    println("i=$i")

    check(i == 19114)
    println("Part 2")
    //val j = part2(testInput)
    //println("j=$j")
    //check(j == 62)

    val input = readInput("Day19")
    part1(input).println()
    // 405901 too high
    //386787

    part2(input).println()
    //8564
}
