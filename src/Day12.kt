import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main() {

    fun matchCondition(damaged: String, pattern: List<Int>, size:Int): Boolean {
        val p = damaged.split(".").filter {
            it != ""
        }.map { it.length }
        return p == pattern.subList(0, size)
    }

    fun matchCondition2(damaged: String, pattern: List<Int>, size:Int): Boolean {
        var count=0
        var index=0
        for (i in damaged.indices) {
            if (damaged[i]=='#') {
                count++
            }
            if (damaged[i]=='.' && count>0) {
                if (index>=size || count!=pattern[index]) {
                    return false
                }
                index++
                count=0
            }
        }
        if (count>0) {
            if (index != size-1 || count != pattern[index]) {
                return false
            }
        } else if (index<size) {
            return false
        }
        return true
    }
    var variant=0
    var total=0



    fun fixOrDamage(damaged: String, pattern: List<Int>)  {

        print("$variant: $damaged\n")
        variant++

        val i = damaged.indexOf('?')
        if (i<0) {

//            val m2 = matchCondition2(damaged, pattern)
//            val m1 = matchCondition(damaged, pattern)
//            if (m1!=m2) {
//                println("$m1!=$m2 $damaged")
//            }
            if (matchCondition(damaged, pattern, pattern.size)) {
                //println("d: $damaged")
                println("si")
                total++
            }
            return
        } else if(i>2 && damaged[i-1]=='.') {
            val part = damaged.substring(0,i-1)
            val p = part.split(".").filter {
                it != ""
            }.map { it.length }
            if (p.size>0) {
                if (p.size>pattern.size) {
                    return
                } else if (matchCondition(part, pattern, p.size)==false) {
                    //println("no")
                    return
                }
            }

        }
        // si hay que matchear un grupo de n # no puede haber un .
        // tomar hasta el 1er . y apuntar al 1er grupo
        // si cant de ? es menor que grupo[1er] entonces ok recursar
        // si cant parcial matchea grupo pasar el siguiente grupo
        val d1 = StringBuilder(damaged)
        d1[i]='.'
        fixOrDamage(d1.toString(), pattern)
        d1[i]='#'
        fixOrDamage(d1.toString(), pattern)
    }

    fun part1(input: List<String>): Int {
        total = 0
        //variant=0
        input.forEach {
            val (damaged, patt) = it.split(" ")
            val pattern = patt.split(",").map { it.toInt()}
            println(it)
            fixOrDamage(damaged, pattern)
            println("-".repeat(20))
        }
        return total
    }

    fun part2Brute(input: List<String>): Int {
        total = 0
        input.forEach {
            val (damaged, patt) = it.split(" ")
            val pattern = patt.split(",").map { it.toInt()}
            println(it)
            val damaged5 = "$damaged?$damaged?$damaged?$damaged?$damaged"
            val pattern5 = mutableListOf<Int>()
            (1..5).forEach {  pattern5.addAll(pattern) }
            fixOrDamage(damaged5, pattern5)
            println("-".repeat(20))
        }
//            fixOrDamage(".??..??...?##.", listOf(1,1,3))
//        var m1 = measureTimeMillis {
//            for (i in 0..10000) {
//                val s = matchCondition("#.###.#.######", listOf(1, 3, 1, 6))
//            }
//        }
// println("m1: $m1 ")
        return total
    }
    fun part2(input: List<String>): Int {
        var total5=0

//        input.forEach {
//            total=0
//            val (damaged, patt) = it.split(" ")
//            val pattern = patt.split(",").map { it.toInt()}
//            println(it)
//            val damaged5 = "$damaged?"
//            fixOrDamage(damaged5, pattern)
//            total5 += (total*4)
//            total=0
//            fixOrDamage(damaged, pattern)
//            total5+=total
//            println("-".repeat(20))
//        }
        fixOrDamage(".??..??...?##.", listOf(1,1,3))
//        var m1 = measureTimeMillis {
//            for (i in 0..10000) {
//                val s = matchCondition("#.###.#.######", listOf(1, 3, 1, 6))
//            }
//        }
// println("m1: $m1 ")
        return total5
    }
    val testInput = readInput("Day12_test")
//        val i = part1(testInput)
//        println("i=$i")
//        check(i == 21)

    val m = measureTimeMillis {
        val j = part2(testInput)
        println("j=$j")
        check(j == 525152)

    }
    println("millis: $m")


    val input = readInput("Day12")
    part1(input).println()
    // 7939

    //part2(input).println()
    //746207878188
}
