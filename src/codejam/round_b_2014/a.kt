package codejam.round_b_2014.a

import common.DataReader
import common.solveAll

fun findBasis(str: String) : String {
    var result = ""

    for (c in str) {
        if (result.isEmpty() || result.last() != c) {
            result += c
        }
    }

    return result
}

fun findOptimal(a: IntArray) : Int {
    fun calc(q: Int) = a.fold(0) { x, y -> x + Math.abs(y - q) }

    var answer = Integer.MAX_VALUE

    for (l in a) {
        answer = Math.min(answer, calc(l))
    }

    return answer
}

fun solveCase(input: DataReader) : String {
    val n = input.nextInt()
    val data = Array(n, { `_` -> input.nextToken() })

    val basis = findBasis(data[0])

    if (data.any { s -> findBasis(s) != basis }) {
        return "Fegla Won"
    }

    var result = 0

    for (c in basis) {
        val lengths = IntArray(n)

        for (i in data.indices) {
            val cur = data[i]
            lengths[i] = cur.indices.firstOrNull { cur[it] != c } ?: cur.length
            data[i] = cur.substring(lengths[i])
        }

        result += findOptimal(lengths)
    }

    return result.toString()
}

fun main(args: Array<String>) {
    solveAll(solver = ::solveCase)
}


