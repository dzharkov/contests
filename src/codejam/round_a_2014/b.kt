package codejam.round_a_2014.b

import common.solveAll
import common.DataReader

fun tryAsRoot(root: Int, edges: Array<out List<Int>>) : Int {
    val n = edges.size
    val subTreeSize = IntArray(n)
    val used = BooleanArray(n)

    fun calcSubTreesSizes(u: Int) {
        used[u] = true

        for (v in edges[u]) {
            if (!used[v]) calcSubTreesSizes(v);
        }

        subTreeSize[u] = edges[u].fold(1) { x, y -> x + subTreeSize[y] }
    }
    calcSubTreesSizes(root)

    used.fill(false)

    val result = IntArray(n)

    fun calcResult(u: Int) {
        used[u] = true
        val children = arrayListOf<Int>()

        for (v in edges[u]) {
            if (!used[v]) {
                calcResult(v)
                children.add(v)
            }
        }

        var currentResult = subTreeSize[u] - 1
        val childrenDeltas = IntArray(children.size)

        for (i in children.indices) {
            val v = children[i]
            childrenDeltas[i] = result[v] - subTreeSize[v]
        }

        childrenDeltas.sort()

        if (childrenDeltas.size >= 2) {
            currentResult += childrenDeltas[0]
            currentResult += childrenDeltas[1]
        }

        result[u] = currentResult
    }
    calcResult(root)

    return result[root]
}

fun solveCase(reader: DataReader) : String {
    val n = reader.nextInt()

    val edges = Array(n, { arrayListOf<Int>() })

    for (`_` in 1..n-1) {
        val u = reader.nextInt() - 1
        val v = reader.nextInt() - 1
        edges[u].add(v)
        edges[v].add(u)
    }

    return ((0..n-1).map { x -> tryAsRoot(x, edges) }).min().toString()
}

fun main(args: Array<String>) {
    solveAll(solver = ::solveCase)
}
