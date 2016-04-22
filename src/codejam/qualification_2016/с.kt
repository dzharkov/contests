package codejam.qualification_2016.с

import common.DataReader
import common.solveAll

fun solveCase(dataReader: DataReader): String {
    val N = dataReader.nextInt()
    val J = dataReader.nextInt()

    val l = (2 shl (N - 2)) + 1
    val r = (2 shl N) - 1

    val result = arrayListOf<Pair<IntArray, LongArray>>()

    outer@for (i in IntProgression.fromClosedRange(l, r, 2)) {
        val mask = IntArray(N) {
            index -> if ((i and (1 shl index)) > 0) 1 else 0
        }

        val divisors = LongArray(9)
        for (p in 2..10) {
            var number = 0L
            var pow = 1L
            for (j in mask.indices) {
                number += pow * mask[j]
                pow *= p
            }

            val divisor = getDivisor(number)
            if (divisor == -1L) {
                continue@outer
            }

            divisors[p - 2] = divisor
        }

        result.add(Pair(mask, divisors))

        if (result.size == J) break
    }

    return "\n" + result.map {
            it.first.joinToString("").reversed() + " " + it.second.joinToString(" ")
    }.joinToString("\n")
}

fun getDivisor(number: Long): Long {
    var i = 2L
    while (i * i <= number) {
        if (number % i == 0L) return i
        i++
    }

    return -1
}

fun main(args: Array<String>) {
    solveAll(solver = ::solveCase)
}
