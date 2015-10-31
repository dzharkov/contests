package codejam.round_3_2014.b

import common.DataReader
import common.solveAll

fun Int.ceilDiv(n: Int) = (this / n) + (if (this % n == 0) 0 else 1)

fun solveCase(inp: DataReader) : String {
    val dianaPower = inp.nextInt()
    val towerPower = inp.nextInt()

    val n = inp.nextInt()

    val healths = IntArray(n)
    val golds = IntArray(n)

    for (i in healths.indices) {
        healths[i] = inp.nextInt()
        golds[i] = inp.nextInt()
    }

    val dp = Array(n) {
        IntArray(1000000).let { it.fill(-1); it }
    }

    fun solve(i: Int, j: Int) : Int {
        if (i == n) return 0
        if (dp[i][j] != -1) return dp[i][j]

        val health = Math.max(healths[i], 0)
        val towerNeedSteps = health.ceilDiv(towerPower)

        var result = solve(i + 1, j + towerNeedSteps)

        for (k in 0..towerNeedSteps-1) {
            val currentHealth = health - k * towerPower
            val dianaNeedSteps = currentHealth.ceilDiv(dianaPower)
            if (dianaNeedSteps <= j + k) {
                result = Math.max(
                        result,
                        solve(i + 1, j + k - dianaNeedSteps) + golds[i]
                )
            }
        }

        dp[i][j] = result

        return result
    }

    return solve(0, 1).toString()
}

fun main(args: Array<String>) {
    solveAll(solver = ::solveCase)
}




