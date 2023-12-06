fun main() {

    // Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
    //Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
    //Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
    //Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
    //Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
    val balls = mapOf("red" to 12, "green" to 13, "blue" to 14)
    fun part1(input: List<String>): Int {
        val r = input.map {
            val match = it.split(":")
            var matchnum = match[0].substring(5).toInt()
            val games = match[1].split(",",";")
            games.forEach { g ->

                val game = g.trim().split(" ")
                val cant = game[0].toInt()
                val color = game[1]
                if (cant>balls[color]!!) {
                    matchnum = 0
                }

            }
            println("m=$matchnum  | $it")
            matchnum
        }
        val s = r.sum()

        return s
    }

    fun part2(input: List<String>): Int {
        val r = input.map {
            val colors = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
            val match = it.split(":")
            val games = match[1].split(",",";")
            games.forEach { g ->

                val game = g.trim().split(" ")
                val cant = game[0].toInt()
                val color = game[1]
                if (cant>colors[color]!!) {
                    colors[color]=cant
                }

            }
            val r=colors["red"]!!
            val g=colors["green"]!!
            val b=colors["blue"]!!
            val power = r*g*b
            println("m=$power r:$r g:$g b:$b  | $it")

            //println("p=$power  | $it")
            power
        }
        val s = r.sum()

        return s

    }

    // test if implementation meets criteria from the description, like:
    //val testInput = readInput("Day02_test")
    //val i = part1(testInput)
    //println("i=$i")
    //check(part1(testInput) == 8)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
