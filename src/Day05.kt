fun main() {

    data class CatMap(
        val destination: Long,
        val start: Long,
        val length: Long,
    )
    val maps = mutableMapOf<String, MutableList<CatMap>>()

    fun findInRange(target: Long, list: List<CatMap>) : Long {
        val aMap = list.find {
            target>=it.start && target<it.start+it.length
            }

        aMap?.let {
            return it.destination + (target - it.start)
        }
        return target
    }

    fun findLocation(seed: Long ) : Long {
        var target = seed
        maps.forEach { category, list ->
            target = findInRange(target, list)
        }
        return target
    }

    fun fillMap(input: List<String>) {
        var mapName=""
        input.drop(2).forEach { s ->
            if (s.contains("map")) {
                mapName = s.split(" ").first()
            } else if (s!="") {
                val (destination, start, length) = s.split(" ").map { it.toLong()}
                //println("d: $destination s:$start l:$length")
                val list = maps.get(mapName)
                if (list == null) {
                    maps.put(mapName, mutableListOf(CatMap(destination, start, length)))
                } else {
                    list.add(CatMap(destination, start, length))
                }
            }
        }
    }

    fun part1(input: List<String>): Long {
        val seeds = input.first().substring(7).split(" ")
        fillMap(input)

        return seeds.map {
            findLocation(it.toLong())
        }.sorted().first()
    }

    fun part2(input: List<String>): Long {
        val seeds = input.first().substring(7).split(" ").map { it.toLong() }
        fillMap(input)

        var r= Long.MAX_VALUE
        seeds.chunked(2).forEach { (seed, length) ->
            for (i in seed..(seed+length)) {
                val location = findLocation(i)
                if (location<r) {
                    r=location
                }
            }
        }
        return r
    }

    val testInput = readInput("Day05_test")
    val i = part1(testInput)
    println("i=$i")
    check(i == 35L)


    val input = readInput("Day05")
    part1(input).println()
    // 825516882


    part2(input).println()
    //136096660
}
