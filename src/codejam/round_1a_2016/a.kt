package codejam.round_1a_2016.a

import common.*

fun solveCase(inp: DataReader) : String {
    val word = inp.next()!!

    return word.substring(1)
            .fold(word[0].toString()) {
                res, next ->
                if (res[0] <= next)
                    next + res
                else
                    res + next
            }
}

fun main(args: Array<String>) {
    solveAll(solver = ::solveCase)
}
