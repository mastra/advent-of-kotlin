typealias Pos = Pair<Int, Int>

fun main() {
//..F7.
//.FJ|.
//SJ.L7
//|F--J
//LJ...

    val n = Pos(-1,0)
    val s = Pos(1,0)
    val w = Pos(0,-1)
    val e = Pos(0, 1)
    val pipes = mapOf(
        '|' to setOf(n,s),
        '-' to setOf(w,e),
        'L' to setOf(n,e),
        'J' to setOf(n,w),
        '7' to setOf(s,w),
        'F' to setOf(s,e),
        'S' to setOf(n,e,s,w)
        )

    fun nextStep(x:Int, y:Int, dir: Pair<Int, Int>, c:Char, input: List<String>) : Pair<Int, Int> {
        val W = input.first().length
        val H = input.size

        return pipes.get(c)!!.filter { (dy,dx) ->
            dy!=dir.first*-1 || dx!=dir.second*-1 }
            .first { (dy, dx) ->
                val nx=x+dx
                val ny=y+dy
                if (nx>=0 && nx<W && ny>=0 && ny<H) {
                    val next = input[ny][nx]
                    val step = pipes.get(next)
                    val found = step?.firstOrNull { (cy, cx) ->
                        cx == dx * -1 && cy == dy * -1
                    }
                    // if (found!=null)
                    //     println("c:$c step: $step ")
                    found!= null
                } else {
                    false
                }
            }

    }


    // s en 0,2
    fun part1(input: List<String>): Int {
        var x= 0
        var y = input.indexOfFirst {
            x = it.indexOf('S')
            x!= -1
        }
        var total = 0
        println("$x $y")
        var step = Pair(0,0)

        var c = 'S'
        do {
            //val (sy, sx) = nextStep(x,y, stepx, stepy, c, input)
            step = nextStep(x,y, step, c, input)
            x+=step.second
            y+=step.first
            println("x:$x y=$y")
            c=input[y][x]
            total++
        } while(c!='S')
        return total/2
    }

    fun part2(input: List<String>): Int {
        var x= 0
        var y = input.indexOfFirst {
            x = it.indexOf('S')
            x!= -1
        }
        var total = 0
        val W = input.first().length
        val H = input.size
        println("$x $y")
        var stepx = 0
        var stepy = 0
        var c = 'S'
        do {
            val (sy, sx) =  pipes.get(c)!!.filter { (dy,dx) ->
                dy!=stepy*-1 || dx!=stepx*-1 }
                .first { (dy, dx) ->
                    val nx=x+dx
                    val ny=y+dy
                    if (nx>=0 && nx<W && ny>=0 && ny<H) {
                        c = input[ny][nx]
                        val step = pipes.get(c)
                        val found = step?.firstOrNull { (cy, cx) ->
                            cx == dx * -1 && cy == dy * -1
                        }
                        // if (found!=null)
                        //     println("c:$c step: $step ")
                        found!= null
                    } else {
                        false
                    }
                }
            stepx = sx
            stepy = sy
            x+=stepx
            y+=stepy
            println("x:$x y=$y")
            total++
        } while(input[y][x]!='S')
        return total/2
    }

    val testInput = readInput("Day10_test")
    val i = part1(testInput)
    println("i=$i")
    check(i == 8)
    //val testInput2 = readInput("Day10_test2")
    //val j = part2(testInput2)
    //println("j=$j")
    //check(j == 4)

    val input = readInput("Day10")
    part1(input).println()
    // 6738

    //part2(input).println()
    // 8245452805243
}
