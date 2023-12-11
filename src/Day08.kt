fun main() {

    data class Node(
            val left: String,
            val right: String
            )


    fun part1(input: List<String>): Int {
        val directions = input.first()
        var nodes = mutableMapOf<String, Node>()

        input.subList(2, input.size). forEach { s ->
            val key = s.substring(0,3)
            val left = s.substring(7,10)
            val right = s.substring(12, 15)
            nodes.put(key, Node(left, right))
        }
        var count = 0
        val s = directions.length
        var current = "AAA"
        while (current != "ZZZ") {
            println(" c: $count $current")
            val instruction = nodes[current]
            val dir = directions.get(count % s)
            current = if (dir == 'L') instruction!!.left else
                    instruction!!.right
            count++
        }
        return count
    }

    fun isTheEnd(current: Set<String>) : Boolean {
        return current.size == current.filter { it.last() == 'Z' }.size
    }

    fun part2BruteForce(input: List<String>): Long {
        val directions = input.first()
        var nodes = mutableMapOf<String, Node>()

        input.subList(2, input.size). forEach { s ->
            val key = s.substring(0,3)
            val left = s.substring(7,10)
            val right = s.substring(12, 15)
            nodes.put(key, Node(left, right))
        }

        //
        var current = nodes.filter { (k,_ ) -> k.last() == 'A'}.keys
        var f = current.size
        var count = 0L
        val s = directions.length


        while (!isTheEnd(current)) {
            //current = current.filter { it.last()!='Z' }.toSet()
            print("$count \r")
            val index = (count % s.toLong()).toInt()
            val dir = directions.get(index)
            val next = mutableSetOf<String>()

            if (dir == 'L') {
                current.forEach { key ->
                    next.add(nodes[key]!!.left)
                }
            } else {
                current.forEach { key ->
                    next.add(nodes[key]!!.right)
                }
            }
            current = next
            count++
        }
        return count
    }
//
//    fun part2FromEnd(input: List<String>): Long {
//        val directions = input.first()
//        var nodes = mutableMapOf<String, Node>()
//
//        input.subList(2, input.size). forEach { s ->
//            val key = s.substring(0,3)
//            val left = s.substring(7,10)
//            val right = s.substring(12, 15)
//            nodes.put(key, Node(left, right))
//        }
//
//        //
//        var currentA = nodes.filter { (k,_ ) -> k.last() == 'A'}.keys
//        var currentZ = nodes.filter { (k,_ ) -> k.last() == 'Z'}.keys
//
//        var count = 0L
//        val s = directions.length
//
//        while (!currentA.containsAll(currentZ)) {
//            print("$count \r")
//            val index = (count % s.toLong()).toInt()
//            val dir = directions.get(index)
//            val nextA = mutableSetOf<String>()
//            val nextZ = mutableSetOf<String>()
//
//            if (dir == 'L') {
//                currentA.forEach { key ->
//                    nextA.add(nodes[key]!!.left)
//                }
//                currentZ.forEach { key ->
//                    nextZ.add(nodes[key]!!.left)
//                }
//            } else {
//                currentA.forEach { key ->
//                    nextA.add(nodes[key]!!.right)
//                }
//                currentZ.forEach { key ->
//                    nextZ.add(nodes[key]!!.right)
//                }
//            }
//            currentA = nextA
//            currentZ = nextZ
//            count++
//        }
//        return count
//    }

    fun part2(input: List<String>): Long {
        val directions = input.first()
        var nodes = mutableMapOf<String, Node>()

        input.subList(2, input.size). forEach { s ->
            val key = s.substring(0,3)
            val left = s.substring(7,10)
            val right = s.substring(12, 15)
            nodes.put(key, Node(left, right))
        }

        //
        var current = nodes.filter { (k,_ ) -> k.last() == 'A'}.keys
        var f = current.size
        var count = 0L
        val s = directions.length
        val ciclos = mutableMapOf<String, Long>()
        var contCiclos = 0
        val maxCiclos = current.size
        while (current.size>0) {
            //current = current.filter { it.last()!='Z' }.toSet()
            print("$count \r")
            val index = (count % s.toLong()).toInt()
            val dir = directions.get(index)
            val next = mutableSetOf<String>()

            if (dir == 'L') {
                current.forEach { key ->
                    next.add(nodes[key]!!.left)
                }
            } else {
                current.forEach { key ->
                    next.add(nodes[key]!!.right)
                }
            }
            count++

            val ended = next.find { it.last() == 'Z' }
            if (ended != null) {
                ciclos.put(ended, count)
                contCiclos++
                current = next.filter {it != ended}.toSet()
            } else {
                current = next
            }
        }

        // calcular LCM de ciclos

        return 2L
    }

    val testInput = readInput("Day08_test")
    val i = part1(testInput)
    println("i=$i")
    check(i == 6)
    val testInput2 = readInput("Day08_test2")

    val j = part2(testInput2)
    println("j=$j")
    check(j == 2L)

    val input = readInput("Day08")
    part1(input).println()
    // 12599

    part2(input).println()
    //248256639
    // 8245452805243
}
