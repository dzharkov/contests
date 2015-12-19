package codejam.qualification_2015.c

import common.DataReader
import common.abs
import common.sign
import common.solveAll
import java.util.*

private val G = arrayOf(
        // 1 i j k
        intArrayOf(1, 2, 3, 4),
        // i -1 k -j
        intArrayOf(2, -1, 4, -3),
        // j -k -1 i
        intArrayOf(3, -4, -1, 2),
        // k j -i -1
        intArrayOf(4, 3, -2, -1))

fun DataReader.solveCase(): String {
    nextInt()
    val x = nextLong()

    val a = next()!!.map {
        when (it) {
            'i' -> 2
            'j' -> 3
            'k' -> 4
            else -> error("Unexpected char: $it")
        }
    }

    val result = a.reduce(Int::multiply).pow(x)
    if (result != -1) return "NO"

    val repeatedChunk = (1..Math.min(8, x)).map { a }.reduce { x, y -> x + y }

    val fromLeftToRight = repeatedChunk.prefixesProducts(fromRightToLeft = false)
    val fromRightToLeft = repeatedChunk.asReversed().prefixesProducts(fromRightToLeft = true)

    val isThereAny = Array(repeatedChunk.size) { BooleanArray(8) }

    for (i in isThereAny.indices) {
        isThereAny[i][fromRightToLeft[i].index] = true
        if (i > 0) {
            for (j in isThereAny[0].indices) {
                isThereAny[i][j] = isThereAny[i][j] || isThereAny[i - 1][j]
            }
        }
    }

    return (0..fromLeftToRight.size - 2).any {
        index ->
        if (fromLeftToRight[index] != 2) return@any false
        val revIndex = fromLeftToRight.size - index - 2
        val restResult = fromRightToLeft[revIndex]

        isThereAny[revIndex][((-3) multiply restResult).index]
    }.toYesNo()
}

fun Boolean.toYesNo() = if (this) "YES" else "NO"

val Int.index: Int get() = (abs() - 1) + (if (sign() < 1) 4 else 0)

infix fun Int.multiply(other: Int) =
        G[this.abs() - 1][other.abs() - 1] * (this.sign() * other.sign())

fun List<Int>.prefixesProducts(fromRightToLeft: Boolean): List<Int> {
    val result = ArrayList<Int>(this.size)

    for (it in this) {
        if (result.isNotEmpty()) {
            result.add(if (!fromRightToLeft) result.last() multiply it else it multiply result.last())
        }
        else {
            result.add(it)
        }
    }

    return result
}

fun Int.pow(_n: Long): Int {
    var n = _n
    var currentMultiplier = this
    var result = 1

    while (n > 0) {
        if (n % 2L == 1L) {
            result = result multiply currentMultiplier
            n--
        }
        else {
            currentMultiplier = currentMultiplier multiply currentMultiplier
            n /= 2L
        }
    }

    return result
}

fun main(args: Array<String>) {
    solveAll(solver = DataReader::solveCase)
}
