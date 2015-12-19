package codejam.qualification_2015.b

import common.DataReader
import common.solveAll

fun DataReader.solveCase(): String {
    val n = nextInt()
    val a = readIntArray(n)

    return (1..a.max()!!).map {
        shouldLeftOnPlates ->
        a.map { v -> (v - 1) / shouldLeftOnPlates }.sum() + shouldLeftOnPlates
    }.min().toString()
}

fun main(args: Array<String>) {
    solveAll(solver = DataReader::solveCase)
}
