package codejam.qualification_2016.b

import common.DataReader
import common.solveAll

fun solveCase(dataReader: DataReader): String {
    val a = dataReader.nextToken().map { it == '+' }.toBooleanArray()
    val n = a.size

    var i = n - 1

    while (i >= 0 && a[i]) {
        i--
    }

    if (i < 0) return "0"

    var result = 0

    while (i >= 0) {
        result++
        i--
        while (i >= 0 && a[i] == a[i + 1]) {
            i--
        }
    }

    return result.toString()
}

fun main(args: Array<String>) {
    solveAll(solver = ::solveCase)
}
