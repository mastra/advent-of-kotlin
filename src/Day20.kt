data class Pulse(
    val source: String,
    val dest: String,
    val v: Int = 0,
    val cycle: Int = 0,
)

sealed class Modulo(
    val name: String,
    val output: List<String>,
) {
    class FlipFlop(name: String, output: List<String>, var state: Int) : Modulo(name, output) {
        fun runCycle(v: Int, cycle: Int): List<Pulse>? {
            if (v == 1)
                return null
            val s = if (state == 1) 0 else 1
            state = s
            return output.map { Pulse(name, it, s, cycle + 1) }
        }
    }

    class Conjuction(
        name: String,
        output: List<String>,
        var state: MutableMap<String, Int> = mutableMapOf()
    ) : Modulo(name, output) {
        fun runCycle(v: Int, cycle: Int, source: String): List<Pulse>? {
            state[source] = v
            val all = state.values.sum()
            val s = if (all==state.size) 0 else 1
            return output.map {
                Pulse(name, it, s, cycle + 1)
            }
        }

        fun addInput(name:String) {
            state[name]=0
        }
    }

    class Pass(name: String, output: List<String>) : Modulo(name, output) {
        fun runCycle(v: Int, cycle: Int): List<Pulse>? {
            return output.map {
                Pulse(name, it, v, cycle + 1)
            }
        }
    }
}

fun main() {


    var circuit = mutableMapOf<String, Modulo>()
    var inputs = mutableListOf<Pulse>()
    var low = 0
    var high = 0

    fun MutableList<Pulse>.send(p: Pulse) {
        this.add(p)
       // println("Pulse send ${p.source} ->  ${p.dest} = ${p.v}")
        if (p.v == 0) {
            low++
        } else {
            high++
        }
    }

    fun Pulse.runCycle() {
        val m = circuit.get(dest) ?: return
        var r: List<Pulse>? = when (m) {
            //%
            is Modulo.FlipFlop ->
                m.runCycle(v, cycle)

            is Modulo.Conjuction ->
                m.runCycle(v, cycle, source)

            is Modulo.Pass -> {
                m.runCycle(v, cycle)
            }
        }
        r?.forEach {
            inputs.send(it)
        }
        //println("L: $low H: $high")
    }

    fun part1(input: List<String>): Int {
        low = 0
        high =0
        circuit = mutableMapOf()
        inputs = mutableListOf()

        input.forEach {
            var (name, outs) = it.split(" -> ")
            var op = 0
            if (!name.first().isLetter()) {
                op = if (name.first() == '%') 1 else 2
                name = name.drop(1)
            }
            val o = outs.split(", ")
            val m: Modulo = when (op) {
                1 -> Modulo.FlipFlop(name, o, 0)
                2 -> Modulo.Conjuction(name, o)
                else -> Modulo.Pass(name, o)
            }
            circuit.put(name, m)
        }

        circuit.forEach { name,m ->
            m.output.map { circuit.get(it) }
                .forEach {
                    if (it is Modulo.Conjuction) {
                        it.addInput(name)
                    }
                }
        }
        circuit.put("output", Modulo.Pass("output", emptyList()))

        for (i in 1..1000) {
            inputs.send(Pulse("button", "broadcaster", 0, 1))

            var cycle = 1
            do {
                val p = inputs.filter { pulse ->
                    pulse.cycle == cycle
                }
                p.forEach { pulse ->

                    println("$cycle:${pulse.dest}]")
                    pulse.runCycle()
                }
                inputs.removeAll(p)
                cycle++
            } while (inputs.isNotEmpty())
            println("i:$i ---------->l:$low h:$high")

        }
        var total = low * high
        return total
    }

    fun part2(input: List<String>): Int {
        low = 0
        high =0
        circuit = mutableMapOf()
        inputs = mutableListOf()

        input.forEach {
            var (name, outs) = it.split(" -> ")
            var op = 0
            if (!name.first().isLetter()) {
                op = if (name.first() == '%') 1 else 2
                name = name.drop(1)
            }
            val o = outs.split(", ")
            val m: Modulo = when (op) {
                1 -> Modulo.FlipFlop(name, o, 0)
                2 -> Modulo.Conjuction(name, o)
                else -> Modulo.Pass(name, o)
            }
            circuit.put(name, m)
        }

        circuit.forEach { name,m ->
            m.output.map { circuit.get(it) }
                .forEach {
                    if (it is Modulo.Conjuction) {
                        it.addInput(name)
                    }
                }
        }
        val xm : Modulo.Conjuction = circuit.get("xm") as Modulo.Conjuction
        val ciclos  = xm.state.map { (k,v) -> k to 0 }.toMap() as MutableMap<String, Int>
        val found = mutableMapOf<String, Int>()
        circuit.put("output", Modulo.Pass("output", emptyList()))
        var i = 1
        while (i<5000) {
            inputs.send(Pulse("button", "broadcaster", 0, 1))

            var cycle = 1
            do {
                val p = inputs.filter { pulse ->
                    pulse.cycle == cycle
                }
                p.forEach { pulse ->

                    //println("$cycle:${pulse.dest}]")
                    pulse.runCycle()
                    if (ciclos.get(pulse.dest)!=null && pulse.v == 0) {
                        val ant = ciclos.get(pulse.dest)!!
                        ciclos.put(pulse.dest, i)
                        println("pulse : ${pulse.dest} i=${i-ant}")
                        found.put(pulse.dest, i-ant)
//                        if (ciclos.all { (k,v) ->
//                            v>0
//                            }) {
//                            println("FINN")
//                            return i
//                        }
                    }

                }
                inputs.removeAll(p)
                cycle++
            } while (inputs.isNotEmpty())

            i++
        }
        found.forEach { t, u ->
            println("Found cycles $t, $u")
        }
        //Found cycles ng, 3803
        //Found cycles ft, 3877
        //Found cycles sv, 3889
        //Found cycles jz, 3917
        println("calcular mcm !")
        //224,602,011,344,203
        //224602011344203
        return 0
    }

    val testInput = readInput("Day20_test")
    val i = part1(testInput)
    println("i=$i")

//    check(i == 32000000)
    check(i == 11687500)
    //println("Part 2")
//val j = part2(testInput)
//println("j=$j")
//check(j == 62)

    val input = readInput("Day20")
    part1(input).println()
    //788081152


    part2(input).println()
}
