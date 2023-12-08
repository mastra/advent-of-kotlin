import java.lang.Integer.max
import kotlin.math.min

fun main() {


    // A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, or 2
    fun Char.pokerValue(): Int {
        val p = "23456789TJQKA"
        return p.indexOf(this)
    }

    fun Char.pokerValue2(): Int {
        val p = "J23456789TQKA"
        return p.indexOf(this)
    }

    class Kind(val card: Char, val count: Int): Comparable<Kind>{
        override fun compareTo(other: Kind): Int {
            if (count == other.count) {
                return card.pokerValue() - other.card.pokerValue()
            }
            return count - other.count
        }
    }

    class Kind2(val card: Char, val count: Int): Comparable<Kind2>{
        override fun compareTo(other: Kind2): Int {
            if (count == other.count) {
                return card.pokerValue2() - other.card.pokerValue2()
            }
            return count - other.count
        }
    }

    class Hand(
        val cards: String,
        val bid: Int,
    ): Comparable<Hand> {
        override fun compareTo(other: Hand): Int {
            val hand = groupByCard()
            val otherhand =  other.groupByCard()
            var i = 0
            while (i<hand.size && i<otherhand.size) {
                if (hand[i].count > otherhand[i].count) {
                    return 1
                } else if (hand[i].count < otherhand[i].count) {
                    return -1
                }
                i++
            }

            // todos dif
            i = 0
            while (i<cards.length) {
                if (cards.get(i)!=other.cards.get(i)) {
                    return cards.get(i).pokerValue() - other.cards.get(i).pokerValue()
                }
                i++
            }
            return 0
        }

        fun groupByCard(): List<Kind> {
            val group = mutableMapOf<Char, Int>()
            cards.forEach { c ->
                val count = if (group.get(c) != null) {
                    group.get(c)!! + 1
                } else {
                    1
                }
                group.put(c,count)
            }

            val r = group.toList().map { (c,i) ->
                Kind(c,i)
            }
            return r.sortedDescending()
        }
    }


    class Hand2(
        val cards: String,
        val bid: Int,
    ): Comparable<Hand2> {
        override fun compareTo(other: Hand2): Int {
            val hand = groupByCard()
            val otherhand =  other.groupByCard()
            var i = 0
            while (i<hand.size && i<otherhand.size) {
                if (hand[i].count > otherhand[i].count) {
                    return 1
                } else if (hand[i].count < otherhand[i].count) {
                    return -1
                }
                i++
            }

            // todos dif
            i = 0
            while (i<cards.length) {
                if (cards.get(i)!=other.cards.get(i)) {
                    return cards.get(i).pokerValue2() - other.cards.get(i).pokerValue2()
                }
                i++
            }
            return 0
        }

        fun groupByCard(): List<Kind2> {
            val group = mutableMapOf<Char, Int>()
            cards.forEach { c ->
                val count = if (group.get(c) != null) {
                    group.get(c)!! + 1
                } else {
                    1
                }
                group.put(c,count)
            }
            if (group.get('J')!=null && group.get('J')!! < 5) {
                group.put('J',1)
            }
            val r = group.toList().map { (c,i) ->
                Kind2(c,i)
            }
            val result = r.sortedDescending().toMutableList()
            val cantJ = cards.map { if (it == 'J') 1 else 0}.sum()

            // siempre me conviene sustituir las J porque son las menores
            val strongest = result.get(0)
            if (strongest.card=='J' && strongest.count==5) {
                return result
            }
            result[0] = Kind2(strongest.card, min(strongest.count + cantJ, 5))
            var indexJ = result.indexOfFirst {
                it.card == 'J'
            }
            if (indexJ>=0) {
                result[indexJ]=Kind2('J',1)
            }

            return result
        }
    }

    fun part1(input: List<String>): Long {
        val hands = input.map { s ->
            val (cards, bid) = s.split(" ")
            Hand(cards, bid.toInt())
        }
        val s = hands.sorted()
        var total =0L
        s.forEachIndexed { index, hand ->
            println("i: ${index+1} hand: ${hand.cards} b: ${hand.bid}")
            total += (index+1) * hand.bid
        }
        return total
    }

    fun part2(input: List<String>): Long {
        val hands = input.map { s ->
            val (cards, bid) = s.split(" ")
            Hand2(cards, bid.toInt())
        }
        val s = hands.sorted()
        var total =0L
        s.forEachIndexed { index, hand ->
            println("i: ${index+1} hand: ${hand.cards} b: ${hand.bid}")
            total += (index+1) * hand.bid
        }
        return total
    }

    val testInput = readInput("Day07_test2")
    val i = part1(testInput)
    println("i=$i")
    //check(i == 6440L)
    check(i == 6592L)
    val j = part2(testInput)
    println("j=$j")
    //check(j == 5905L)
    check(j == 6839L)

    val input = readInput("Day07")
    part1(input).println()
    // 246424613

    part2(input).println()
    //248256639
}
