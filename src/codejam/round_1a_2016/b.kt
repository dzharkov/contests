package codejam.round_1a_2016.b


import common.DataReader
import common.solveAll
import kotlin.reflect.KProperty1

fun solveCase(inp: DataReader): String {
    val n = inp.nextInt()

    val linesNumber = 2 * n - 1
    val lines = (1..linesNumber).map {
        (1..n).map { inp.nextInt() }.toIntArray()
    }.toMutableList()

    val freeIndices = (0 until linesNumber).toMutableList()
    val result = mutableListOf<Pair<Int?, Int?>>()

    for (i in 0 until n) {
        val minValue = freeIndices.map { index -> lines[index][i] }.min()
        val relevantLinesIndices = freeIndices.filter { index ->
            lines[index][i] == minValue
        }

        assert(relevantLinesIndices.size in 1..2)

        result.add(
                Pair(relevantLinesIndices[0],
                     relevantLinesIndices.getOrNull(1)))

        freeIndices.removeAll(relevantLinesIndices)
    }

    val notFixed = (0 until n).toSortedSet()

    fun fix(current: Int) {
        assert(notFixed.remove(current))

        for (i in 0 until n) {
            val myHorValue = result[current].second?.let { lines[it][i] }
            val myVerValue = result[current].first?.let { lines[it][i] }

            val hor1Value = result[i].first?.let { lines[it][current] }
            val ver1Value = result[i].second?.let { lines[it][current] }

            val hor2Value = result[i].second?.let { lines[it][current] }
            val ver2Value = result[i].first?.let { lines[it][current] }

            if (!isOneNullOrEqual(hor1Value, myHorValue) ||
                    !isOneNullOrEqual(ver1Value, myVerValue)) {
                result[i] = Pair(result[i].second, result[i].first)
                fix(i)
            }
            else if (!isOneNullOrEqual(hor2Value, myHorValue) ||
                        !isOneNullOrEqual(ver2Value, myVerValue)) {
                if (i in notFixed) {
                    fix(i)
                }
            }
        }
    }

    while (!notFixed.isEmpty()) {
        fix(notFixed.first())
    }

    val (index, propertyReference) =
        result.withIndex().first {
            it.value.first == null || it.value.second == null
        }.let {
            Pair(it.index,
                    if (it.value.first == null)
                        Pair<Int?, Int?>::second as KProperty1<Pair<Int?, Int?>, Int?>
                    else
                        Pair<Int?, Int?>::first as KProperty1<Pair<Int?, Int?>, Int?>)
        }

    val answer = (0 until n).map { i ->
        lines[propertyReference.get(result[i])!!][index]
    }

    return answer.joinToString(" ")
}

private fun isOneNullOrEqual(a: Any?, b: Any?) =
        if (a != null && b != null)
            a == b
        else
            true

fun main(args: Array<String>) {
    solveAll(solver = ::solveCase)
}


