import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime


fun main() {

    fun printPlatform(platform:List<CharArray>) {
        val l = platform[0].size
        for (y in 0..<platform.size) {
            for (x in 0..<l) {
                print(platform[y][x])
            }
            println()
        }
        println()
    }

    fun northLoad(platform: List<CharArray>) : Int {
        val l = platform[0].size
        var total = 0
        for (y in 0..<platform.size) {
            for (x in 0..<l) {
                if (platform[y][x]=='O') {
                    total += (platform.size-y)
                }
            }
        }
        return total
    }
    fun fallTo(platform: List<CharArray>, y:Int, x:Int) :Int {
        var f = y
        while (f>0 && platform[f-1][x]=='.' ) {
            f--
        }
        return f
    }

    fun part1(input: List<String>): Int {
        val l = input.first().length
        var platform = input.map {
            it.toCharArray()
        }
        for (y in 1..<platform.size) {
            for (x in 0..<l) {
                if (platform[y][x]!='O')
                    continue;
                val f = fallTo(platform, y,x)
                if (f!=y) {
                    platform[f][x] = 'O'
                    platform[y][x]= '.'
                }
            }

        }

        var total = 0
        for (y in 0..<platform.size) {
            for (x in 0..<l) {
                if (platform[y][x]=='O') {
                    total += (platform.size-y)
                }
            }
        }
        return total
    }

    fun tiltNorth(platform: List<CharArray>, rockColumns:Array<MutableList<Int>>) {
        val h = platform.size
        val w = platform[0].size
        for (x in 0..<w) {
            var y0 = 0
            for (i in 0..rockColumns[x].size) {
                //val y0 = rockColumns[x][i]
                val y1 = if (i == rockColumns[x].size)
                    h else
                    rockColumns[x][i]
                var cont = 0
                for (y in y0..<y1) {
                    if (platform[y][x] == 'O') {
                        cont++
                    }
                }
                if (cont > 0) {
                    for (y in y0..<y1) {
                        platform[y][x] = if (cont-- > 0) 'O' else '.'
                    }
                }
                y0 = y1+1
            }
        }
    }

    fun tiltSouth(platform: List<CharArray>, rockColumns:Array<MutableList<Int>>) {
        val h = platform.size
        val w = platform[0].size
        for (x in 0..<w) {
            var y0 = h-1
            val column = rockColumns[x].reversed()
            for (i in 0..column.size) {
                //val y0 = rockColumns[x][i]
                val y1 = if (i == column.size)
                    -1 else
                    column[i]
                var cont = 0
                for (y in y0 downTo y1+1) {
                    if (platform[y][x] == 'O') {
                        cont++
                    }
                }
                if (cont > 0) {
                    for (y in y0 downTo y1+1) {
                        platform[y][x] = if (cont-- > 0) 'O' else '.'
                    }
                }

                y0 = y1-1
            }
        }
    }

    fun tiltWest(platform: List<CharArray>, rockRows:Array<MutableList<Int>>) {
        val h = platform.size
        val w = platform[0].size
        for (y in 0..<h) {
            var x0 = 0
            for (i in 0..rockRows[y].size) {
                val x1 = if (i == rockRows[y].size)
                    w else
                    rockRows[y][i]
                var cont = 0
                for (j in x0..<x1) {
                    if (platform[y][j] == 'O') {
                        cont++
                    }
                }
                if (cont > 0) {
                    for (j in x0..<x1) {
                        platform[y][j] = if (cont-- > 0) 'O' else '.'
                    }
                }
                x0 = x1+1
            }
        }
    }

    fun tiltEast(platform: List<CharArray>, rockRows:Array<MutableList<Int>>) {
        val h = platform.size
        val w = platform[0].size

        for (y in 0..<h) {
            val row  = rockRows[y].reversed()
            var x0 = w-1
            for (i in 0..row.size) {
                val x1 = if (i == row.size)
                    -1 else
                    row[i]
                var cont = 0
                for (j in x0 downTo x1+1) {
                    if (platform[y][j] == 'O') {
                        cont++
                    }
                }
                if (cont > 0) {
                    for (j in x0 downTo x1+1) {
                        platform[y][j] = if (cont-- > 0) 'O' else '.'
                    }
                }
                x0 = x1-1
            }
        }
    }

     class Cycle(s:Int) : Comparator<Cycle> {
         var cont: Int =0
         val start: Int = s
         var ant: Int =0
         var dif: Int = s
         val past: MutableList<Int> = mutableListOf<Int>()
         override fun compare(o1: Cycle?, o2: Cycle?): Int {
             return compareValues(o1?.cont, o2?.cont)
         }
     }

    fun part2(input: List<String>): Int {
        val l = input.first().length
        var platform = input.map {
            it.toCharArray()
        }
        val h = l
        val rockColumns = Array(h) {
            mutableListOf<Int>()
        }
        val  w = platform.size
        val rockRows = Array(w) {
            mutableListOf<Int>()
        }

        println("init")
        for (y in 0..<platform.size) {
            for (x in 0..<l) {
                print(platform[y][x])
                if (platform[y][x]=='#') {
                    rockColumns[x].add(y)
                    rockRows[y].add(x)
                }
            }
            println()
        }
        println("-----------------")
        var total = 0
        //1.000.000.000
        var load = mutableMapOf<Int, Cycle>()
        val d = measureTimeMillis {
            for (i in 1..1000000)  {

                tiltNorth(platform, rockColumns)
                tiltWest(platform, rockRows)
                tiltSouth(platform, rockColumns)
                tiltEast(platform, rockRows)
//                if (i % 1000000 == 0) {
//                    println("$i -------------------")
//                    printPlatform(platform)
//                }
            val l = northLoad(platform)
                val v = load.get(l)
                if (v== null) {
                    load.put(l, Cycle(i))
                } else {
                    v.cont = v.cont +1

                    v.dif = i - v.ant
                    v.ant = i
                    if (v.cont<100)
                        v.past.add(i)
                    //load.put(l, v)
                }
            }
        }
        println("duration $d")
        println("FINAL -------------------")
        val ls = load.toList().sortedByDescending { (_ , value) ->
            value.cont
        }.toMap()
        ls.filter{ (key,v) -> v.cont>10}.forEach { (key, v) ->

            val times = v.cont
            val promedio = v.dif
            val start = v.start
            val dif1 = v.past[1]-v.past[0]
            val dif2 = v.past[2]-v.past[1]
            var sum =0
            var a = 0.0
            var ai = 0L
            if (dif1==dif2) {
                sum = start + (v.cont*dif1)
                a = (1000000000L-v.start).toDouble()/dif1
                ai = (a.toLong()*dif1) + start
            } else {
                sum = start + ( (v.cont/2)* (dif1+dif2))
                a = (1000000000L-v.start).toDouble()/(dif1+dif2)
                ai = (a.toLong()*(dif1+dif2) )+ start
            }

            println("load: $key, times:$times start=$start dif1=$dif1 dif2=$dif2 sum=$sum a=$a ai=$ai")
        }
        //printPlatform(platform)
        total = northLoad(platform)
        return total
    }

    val testInput = readInput("Day14_test")
    //val i = part1(testInput)
    //println("i=$i")
    //check(i == 136)
    //val j = part2(testInput)
    //println("j=$j")
    //check(j == 64)

    val input = readInput("Day14")
    //part1(input).println()
    //109638

    part2(input).println()
    //102647 too low
    //102645
    // 102657
}
