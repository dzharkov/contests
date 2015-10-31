package codejam.round_c_2014.a

import common.DataReader
import common.solveAll

fun gcd(a_: Long, b_: Long): Long {
    var (a,b) = Pair(a_, b_)

    while (a > 0 && b > 0) {
        if (a > b) {
            a %= b
        } else {
            b %= a
        }
    }

    return a + b
}

val ANCESTORS_COUNT = 1L shl 40

fun solveCase(input: DataReader) : String {
    val splitted = input.nextToken().split('/').map { it.toLong() }
    var (nom, denom) = Pair(splitted[0], splitted[1])
    val g = gcd(nom, denom)
    nom /= g
    denom /= g

    if (ANCESTORS_COUNT % denom > 0) {
        return "impossible"
    }

    var result = 0
    while (nom < denom) {
        result++
        nom *= 2L
    }

    return result.toString()
}

fun main(args: Array<String>) {
    solveAll(solver = ::solveCase)
}



