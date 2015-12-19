package codejam.qualification_2015.a

import common.DataReader
import common.solveAll

fun solveCase(dataReader: DataReader): String {
    val n = dataReader.nextInt()
    val s = dataReader.nextToken()
    assert(s.length == n + 1)
    val a = s.map { it - '0' }

    var currentSum = 0
    var lack = 0

    for (i in a.indices) {
        if (currentSum < i) {
            lack += i - currentSum
            currentSum += i - currentSum
        }
        currentSum += a[i]
    }

    return lack.toString()
}

fun main(args: Array<String>) {
    solveAll(solver = ::solveCase)
}
