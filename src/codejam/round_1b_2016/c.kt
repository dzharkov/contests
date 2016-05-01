package codejam.round_1b_2016.c

import common.*

fun solveCase(inp: DataReader) : String {
    val map1 = mutableMapOf<String, Int>()
    val map2 = mutableMapOf<String, Int>()
    val edges = mutableListOf<MutableList<Int>>()
    val first = mutableSetOf<Int>()

    val n = inp.nextInt()

    repeat(n) {
        val u = map1.getOrPut(inp.nextToken(), {
            edges.add(mutableListOf())
            map1.size
        })
        val v = map2.getOrPut(inp.nextToken(), { map2.size })

        edges[u].add(v)

        first.add(u)
    }

    val used = BooleanArray(first.size)
    val secondSize = map2.size
    val prev = IntArray(secondSize) { -1 }

    fun dfs(u: Int): Boolean {
        if (used[u]) return false

        used[u] = true

        val next = edges[u].firstOrNull {
            v ->
            prev[v] == -1 || dfs(prev[v])
        } ?: return false

        prev[next] = u

        return true
    }

    first.forEach {
        dfs(it)
        used.fill(false)
    }

    val additionalCount = first.size - prev.count { it != -1 }
    val result = n - (secondSize + additionalCount)

    return "$result"
}

fun main(args: Array<String>) {
    solveAll(solver = ::solveCase)
}
