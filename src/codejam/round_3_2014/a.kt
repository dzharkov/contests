package codejam.round_3_2014.a

import common.DataReader
import common.solveAll

val RIGHT_SEGMENT = 0
val LEFT_SEGMENT = 1
val CENTER_SEGMENT = 2

fun solveCase(inp: DataReader) : String {
    val n = inp.nextInt()
    val p = inp.nextLong()
    val q = inp.nextLong()
    val r = inp.nextLong()
    val s = inp.nextLong()

    val a = LongArray(n)
    a.indices.forEach { i -> a[i] = (i.toLong() * p + q) % r + s }

    val sum = LongArray(n)
    sum[0] = a[0]
    for (i in 1..a.lastIndex) {
        sum[i] += sum[i-1] + a[i]
    }

    fun rsq(i: Int, j: Int) : Long {
        if (i > j || i < 0 || i > sum.lastIndex || j < 0 || j > sum.lastIndex) {
            return 0L
        }

        return sum[j] - (if (i == 0) 0L else sum[i-1])
    }

    fun solveigsChoice(l: Int, r: Int) : Int {
        val results = LongArray(3)
        results[RIGHT_SEGMENT] = rsq(r+1, a.lastIndex)
        results[LEFT_SEGMENT] = rsq(0, l-1)
        results[CENTER_SEGMENT] = rsq(l, r)

        return results.indices.maxBy { results[it] }!!
    }

    fun findStartOf(i: Int, choice: Int) : Int {
        var l = 1
        var r = n - i
        while (l < r) {
            val m = (l + r) / 2

            if (solveigsChoice(i, i + m - 1) < choice) {
                l = m + 1
            } else {
                r = m
            }
        }

        if (l > r) {
            return -1
        }

        return if (solveigsChoice(i, i + l - 1) == choice) i + l - 1 else -1
    }

    var minSolveigs = rsq(0, a.lastIndex)

    for (i in a.indices) {
        val variants = intArrayOf(
                i, n - 1, findStartOf(i, LEFT_SEGMENT), findStartOf(i, CENTER_SEGMENT)
        )

        val variants2 = arrayListOf<Int>()

        for (j in variants) {
            variants2.add(j)
            variants2.add(j-1)
        }

        for (j in variants2) {
            if (j < i) continue
            val choice = solveigsChoice(i, j)
            val res = when (choice) {
                LEFT_SEGMENT -> rsq(0, i-1)
                CENTER_SEGMENT -> rsq(i, j)
                RIGHT_SEGMENT -> rsq(j+1, n-1)
                else -> throw RuntimeException(choice.toString())
            }

            if (res < minSolveigs) {
                minSolveigs = res
            }
        }
    }

    val maxResult = (rsq(0, n-1) - minSolveigs).toDouble() / rsq(0, n-1).toDouble()

    return maxResult.toString()
}

fun main(args: Array<String>) {
    solveAll(solver = ::solveCase)
}



