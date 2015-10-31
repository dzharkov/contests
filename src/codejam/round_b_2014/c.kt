package codejam.round_b_2014.c

import common.DataReader
import common.solveAll
import java.util.*

enum class VertexState {
    NOT_VISITED, IN_PROCESS, VISITED
}

fun solveCase(input: DataReader) : String {
    val n = input.nextInt()
    val m = input.nextInt()

    val zips = IntArray(n).map { input.nextInt() }
    val edges = Array(n, { arrayListOf<Int>() })

    for (`_` in 1..m) {
        val u = input.nextInt() - 1
        val v = input.nextInt() - 1
        edges[u].add(v)
        edges[v].add(u)
    }

    val root = zips.indices.minBy { zips[it] }!!

    val resultPath = arrayListOf<Int>()
    resultPath.ensureCapacity(2 * n)

    val vertexState = Array(n, { VertexState.NOT_VISITED })

    fun isAllAvailable() : Boolean {
        val were = BooleanArray(n)
        val queue = ArrayDeque<Int>(n)
        were[root] = true
        queue.add(root)

        while (queue.isNotEmpty()) {
            val current = queue.pop()
            for (v in edges[current]) {
                if (!were[v] && vertexState[v] != VertexState.VISITED) {
                    queue.add(v)
                    were[v] = true
                }
            }
        }

        return edges.indices.all { v -> were[v] || vertexState[v] != VertexState.NOT_VISITED }
    }

    val treePath = arrayListOf<Int>()

    fun findBestChild(u: Int) = edges[u].filter { vertexState[it] == VertexState.NOT_VISITED }.minBy { zips[it] }

    fun needGoBack() : Boolean {
        val bestChild = findBestChild(treePath.last()) ?: return true

        val result = (0..treePath.lastIndex - 1).reversed().any {
            vertexState[treePath[it + 1]] = VertexState.VISITED

            val best = findBestChild(treePath[it])

            best != null && zips[best] < zips[bestChild] && isAllAvailable()
        }

        treePath.forEach {
            vertexState[it] = VertexState.IN_PROCESS
        }

        return result
    }

    fun buildPath(current: Int) {
        treePath.add(current)
        resultPath.add(current)
        vertexState[current] = VertexState.IN_PROCESS

        while (!needGoBack()) {
            buildPath(findBestChild(current)!!)
        }

        vertexState[current] = VertexState.VISITED
        treePath.remove(treePath.size - 1)
    }
    buildPath(root)

    return resultPath.asSequence().map { zips[it].toString() }.reduce { x, y -> x.concat(y) }
}

fun main(args: Array<String>) {
    solveAll(solver = ::solveCase)
}



