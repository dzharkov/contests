package codejam.round_1b_2016.b

import common.*


fun solveCase(inp: DataReader) : String {
    val a = inp.nextToken().toList()
    val b = inp.nextToken().toList()

    val dp = Array(3) { arrayOfNulls<Pair<Long, Long>>(a.size) }

    val pow = Array(20) {
        (0 until it).fold(1L) { x, y -> x * 10L }
    }

    for (i in a.indices.reversed()) {
        for (currentSign in -1..1) {
            val x = a[i]
            val y = b[i]

            val r1 = if (x == '?') 0..9 else x - '0'..x - '0'
            val r2 = if (y == '?') 0..9 else y - '0'..y - '0'

            var result: Pair<Long, Long>? = null

            for (u1 in r1) {
                for (u2 in r2) {
                    val nextSign =
                            if (currentSign == 0 && u1 != u2)
                                (u1 - u2) / Math.abs(u1 - u2).toInt()
                            else
                                currentSign

                    val next = dp[nextSign + 1].getOrNull(i + 1)

                    val cur = Pair(
                            pow[a.size - i - 1] * u1.toLong() + (next?.first ?: 0),
                            pow[a.size - i - 1] * u2.toLong() + (next?.second ?: 0))

                    if (isBetter(cur, result, currentSign)) {
                        result = cur
                    }
                }
            }

            dp[currentSign + 1][i] = result
        }
    }


    val s = dp[1][0]!!

    return "${s.first.l(a.size)} ${s.second.l(a.size)}"
}

fun Long.l(size: Int): Any {
    return toString().padStart(size, '0')
}

val Pair<Long, Long>.d: Long get() = first - second

fun isBetter(cur: Pair<Long, Long>, result: Pair<Long, Long>?, prevSign: Int): Boolean {
    if (result == null) return true

    val d1 = cur.d - result.d
    if (d1 != 0L) {
        if (prevSign != 0) {
            return d1.sign() != prevSign
        }

        return Math.abs(cur.d) < Math.abs(result.d)
    }

    if (cur.first != result.first) return cur.first < result.first
    if (cur.second != result.second) return cur.second < result.second

    return false
}


fun main(args: Array<String>) {
    solveAll(solver = ::solveCase)
}
