package codejam.round_1b_2016.a

import common.*

//

fun solveCase(inp: DataReader) : String {

    val count = IntArray(26)

    inp.nextToken().forEach {
        count[it - 'A']++
    }

    val words = listOf(
            "ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE"
    )

    val map = listOf(
            'Z' to 0, 'W' to 2, 'X' to 6,
            'G' to 8, 'U' to 4, 'O' to 1, 'R' to 3,
            'F' to 5, 'V' to 7, 'N' to 9)
            .toMap()

    val all = words.toMutableList()
    map.forEach {
        entry ->
        all.remove(all.single { entry.key.toChar() in it })
    }

    assert(all.isEmpty())

    val result = arrayListOf<Int>()

    map.forEach {
        while (count[it.key.toChar() - 'A'] > 0) {
            words[it.value].forEach {
                c ->
                val left = count[c - 'A']--
                assert(left >= 0)
            }

            result.add(it.value)
        }
    }


    return result.sorted().joinToString("")
}

fun main(args: Array<String>) {
    solveAll(solver = ::solveCase)
}
