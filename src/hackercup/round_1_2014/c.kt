package hackercup.round_1_2014.c

import common.DataReader
import common.solveAll
import java.io.BufferedReader
import java.util.StringTokenizer
import java.io.FileReader
import java.io.PrintWriter
import java.util.ArrayDeque
import java.util.Queue

val MAX_N = 2000
val MODULO = 1000000007
val dpStrictGreater = Array(MAX_N + 1) { IntArray(MAX_N + 1) }
val dpNonStrictLess = Array(MAX_N + 1) { IntArray(MAX_N + 1) }

fun main(args: Array<String>) {
    dpNonStrictLess[0][0] = 1
    dpStrictGreater[1][0] = 1
    
    for (i in 0..MAX_N) {
        for (j in 0..MAX_N) {
            if (i > j && i > 1) {
                dpStrictGreater[i][j] = dpStrictGreater[i - 1][j]
                if (j > 0) {
                    dpStrictGreater[i][j] += dpStrictGreater[i][j-1]
                    dpStrictGreater[i][j] %= MODULO
                }
            }
            if (i <= j && j > 0) {
                dpNonStrictLess[i][j] = dpNonStrictLess[i][j-1]
                if (i > 0) {
                    dpNonStrictLess[i][j] += dpNonStrictLess[i-1][j]
                    dpNonStrictLess[i][j] %= MODULO
                }
            }
        }
    }
    
    solveAll(solver = ::solveCase)
}

fun solveCase(inp: DataReader): String {
    val x = inp.nextToken().split('-')
    val (myPoints, otherPoints) = Pair(x[0].toInt(), x[1].toInt())

    return "${dpStrictGreater[myPoints][otherPoints]} ${dpNonStrictLess[otherPoints][otherPoints]}"
}




