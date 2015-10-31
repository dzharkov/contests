package hackercup.round_1_2014.d

import common.DataReader
import common.solveAll


fun main(args: Array<String>) {
    Thread({solveAll(solver = ::solveCase)}).run()
}

fun solveCase(inp: DataReader): String {
    val n = inp.nextInt()
    inp.nextInt()
    
    val children = Array(n) { arrayListOf<Int>() }
    
    for (i in 1..n-1) {
        children[inp.nextInt() - 1].add(i)
    }
    
    val dp = Array(n) { IntArray(4).let { it.fill(-1); it } }
    
    fun dfs(u: Int, v: Int): Int {
        if (v == -1 || dp[u][v] == -1) {
            val result = (1..3).asSequence().filter { it != v }.
                    map { currentCost ->
                        currentCost + children[u].map { child -> dfs(child, currentCost) }.sum()
                    }.min()!!
            if (v >= 0) {
                dp[u][v] = result
            }
            return result
        }
        return dp[u][v]
    }

    return dfs(0, -1).toString()
}




