package codejam.round_a_2014.a

import common.DataReader
import common.solveAll

fun applyConf(switchConf: BooleanArray, outlets: Array<BooleanArray>) : Array<BooleanArray> {
    val result = Array(outlets.size, { BooleanArray(switchConf.size) })

    for (i in outlets.indices)
        for (j in outlets[0].indices) {
            result[i][j] = outlets[i][j]
        }

    switchConf.withIndex().asSequence().filter { p -> p.value }.map { p -> p.index }.forEach {
        j -> for (i in outlets.indices) {
            result[i][j] = !result[i][j]
        }
    }

    return result
}

fun BooleanArray.mask() : Long {
    var result = 0L
    withIndex().asSequence().filter { p -> p.value }.map { p -> p.index }.forEach {
        i -> result = result or (1L shl i)
    }

    return result
}

fun canBeMatched(a: Array<BooleanArray>, b: Array<BooleanArray>) : Boolean {
    val shouldBe = hashMapOf<Long, Int>()

    for (x in a) {
        shouldBe[x.mask()] = (shouldBe[x.mask()] ?: 0) + 1
    }

    for (x in b) {
        shouldBe[x.mask()] = (shouldBe[x.mask()] ?: 0) - 1
    }

    return shouldBe.all { x -> x.value == 0 }
}

fun solveCase(reader: DataReader) : String {
    val n = reader.nextInt()
    val l = reader.nextInt()

    fun readFlow() : Array<BooleanArray> {
        val result = Array(n, { BooleanArray(l) })

        for (i in result.indices) {
            reader.nextToken().withIndex().forEach {
                p -> result[i][p.index] = p.value == '1'
            }
        }

        return result
    }

    val outlets = readFlow()
    val devices = readFlow()

    fun matchedSwitchConf(outlet: BooleanArray, needed: BooleanArray) : BooleanArray {
        val result = BooleanArray(l)

        for (j in result.indices) {
            result[j] = outlet[j] != needed[j]
        }

        return result
    }

    var answer = l + 1

    for (outlet in outlets) {
        val conf = matchedSwitchConf(outlet, devices[0])
        val applied = applyConf(conf, outlets)

        if (canBeMatched(applied, devices)) {
            answer = Math.min(conf.count { x -> x }, answer)
        }
    }

    return if (answer > l) "NOT POSSIBLE" else answer.toString()
}

fun main(args: Array<String>) {
    solveAll(solver = ::solveCase)
}

