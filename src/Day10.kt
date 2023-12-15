//typealias Pos = Pair<Int, Int>

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

    fun findChar(c:Char, input: List<String>): Pair<Int, Int> {
        var x= 0
        var y = input.indexOfFirst {
            x = it.indexOf(c)
            x!= -1
        }
        return Pair(y,x)
    }
    // s en 0,2
    fun part1(input: List<String>): Int {
        var (y,x) = findChar('S', input)
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

    fun lastInLine(mapa : Array<Array<Char>>, y:Int, x:Int):Boolean {
        for (j in x..<mapa[y].size) {
            if (mapa[y][j]!='.') {
                return false
            }
        }
        return true
    }

    fun grafico(c:Char) = when(c) {
        'F' -> '⎾'
        '7' -> '⏋'
        'J' -> '⏌'
        'L' -> '⎿'
        '-' -> '⎯'
        '|' -> '│'
        else -> c
    }
    fun part2(input: List<String>): Int {

        val W = input.first().length
        val H = input.size
        var mapa : Array<Array<Char>> = Array(H) {
            Array(W) { '.'}
        }
        var (y,x) = findChar('S', input)
        var total = 0
        println("$x $y")
        var step = Pair(0,0)
        var c = 'S'
        do {
            mapa[y][x]=c
            //val (sy, sx) = nextStep(x,y, stepx, stepy, c, input)
            step = nextStep(x,y, step, c, input)
            x+=step.second
            y+=step.first
            println("x:$x y=$y")
            c=input[y][x]
            total++
        } while(c!='S')

        var contI =0
//        for (i in 0..<H) {
//            var inside = 0
//            for (j in 0..<W) {
//                print(mapa[i][j])
//            }
//            println()
//        }
        for (i in 0..<H) {
            var inside = 0
            for (j in 0..<W) {
                if (mapa[i][j]!='.') {
                    inside++
                }
                if (inside % 2 ==1 && mapa[i][j]=='.' && !lastInLine(mapa, i,j)) {
                    contI++
                    mapa[i][j]='I'
                }
                print(mapa[i][j])

            }
            println()
        }


        return contI
    }

    val testInput = readInput("Day10_test")
    val i = part1(testInput)
    println("i=$i")
    check(i == 8)
    val testInput2 = readInput("Day10_test3")
    val j = part2(testInput2)
    println("j=$j")
    check(j == 8)

    val input = readInput("Day10")
    part1(input).println()
    // 6738

    part2(input).println()
    // 8245452805243
}
