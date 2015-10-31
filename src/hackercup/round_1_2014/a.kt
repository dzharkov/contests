package hackercup.round_1_2014.a

import common.DataReader
import common.solveAll
import java.io.BufferedReader
import java.io.FileReader
import java.io.PrintWriter
import java.util.*

val MAX_N = 10000000
val isPrime = IntArray(MAX_N + 1)

fun main(args: Array<String>) {
    for (i in 2..MAX_N) {
        if (isPrime[i] == 0) {
            IntProgression(i, MAX_N, i).forEach { j -> isPrime[j]++ }
        }
    }
    solveAll(solver = ::solveCase)
} 

fun solveCase(inp: DataReader): String {
    val (a,b) = Pair(inp.nextInt(), inp.nextInt())
    val k = inp.nextInt()
    return (a..b).count { isPrime[it] == k }.toString()
}



