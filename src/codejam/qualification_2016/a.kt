package codejam.qualification_2016.a

import common.DataReader
import common.solveAll
import java.io.BufferedReader
import java.io.FileInputStream

fun solveCase(dataReader: DataReader): String {
    val n = dataReader.nextInt()

    if (n == 0) return "INSOMNIA"

    val found = mutableSetOf<Int>()
    for (x in generateSequence(1) { x -> x + 1 }.map { it.toLong() * n }) {
        var m = x
        while (m > 0) {
            found.add((m % 10).toInt())
            m /= 10
        }

        if (found.size == 10) return x.toString()
    }

    throw IllegalStateException()
}

fun main(args: Array<String>) {
    solveAll(solver = ::solveCase)
}
