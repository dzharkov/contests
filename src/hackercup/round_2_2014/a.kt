package hackercup.round_2_2014.a

import common.DataReader
import common.solveAll

fun solveCase(inp: DataReader) : String {
    val n = inp.nextInt()
    val x = inp.nextInt()

    val a = IntArray(n).map { inp.nextInt() }.sorted()

    var i = 0
    var j = a.lastIndex

    var result = 0

    while (i < j) {
        if (a[i] + a[j] <= x) {
            i++
            j--
        } else {
            j--
        }
        result++
    }

    result += if (i == j) 1 else 0

    return result.toString()
}

fun main(args: Array<String>) {
    solveAll(solver = ::solveCase)
}




