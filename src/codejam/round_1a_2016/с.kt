package codejam.round_1a_2016.c

import common.DataReader
import common.solveAll

fun solveCase(inp: DataReader): String {
    val n = inp.nextInt()

    val next = (0 until n).map { inp.nextInt() - 1 }
    val prev = Array(n) { arrayListOf<Int>() }

    next.withIndex().forEach { prev[it.value].add(it.index) }

    val isOnCycle = BooleanArray(n)
    val cycles = mutableListOf<List<Int>>()

    for (i in 0 until n) {
        val visitedInCurrentTour = BooleanArray(n)

        var current = i
        while (!visitedInCurrentTour[current]) {
            visitedInCurrentTour[current] = true
            current = next[current]
        }

        val firstElement = current
        if (isOnCycle[firstElement]) continue

        val cycle = generateSequence(next[firstElement]) { next[it] }
                        .takeWhile { it != firstElement }.toList() + firstElement

        cycle.forEach { isOnCycle[it] = true }
        cycles.add(cycle)
    }

    fun maxDepth(u: Int): Int =
            prev[u].filterNot { isOnCycle[it] }
                    .map { 1 + maxDepth(it) }.max() ?: 0

    val maxCycle = cycles.map(Collection<Int>::size).max()!!

    return Math.max(
                maxCycle,
                cycles.filter { it.size == 2 }
                       .sumBy { cycle -> 2 + cycle.map(::maxDepth).sum() })
            .toString()
}

fun main(args: Array<String>) {
    solveAll(solver = ::solveCase)
}



