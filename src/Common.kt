package common

import java.io.BufferedReader
import java.io.FileReader
import java.io.PrintWriter
import java.util.*

class DataReader(private val reader: BufferedReader) {
    var st : StringTokenizer? = null
    companion object {
        fun createFromFile(name: String) = DataReader(BufferedReader(FileReader(name)))
    }

    fun next() : String? {
        while (st == null || !st!!.hasMoreTokens()) {
            val s = reader.readLine() ?: return null
            st = StringTokenizer(s)
        }

        return st?.nextToken()
    }

    fun nextToken() = next()!!

    fun nextInt() = nextToken().toInt()
    fun nextLong() = nextToken().toLong()
    fun readIntArray(n: Int) : IntArray {
        val result = IntArray(n)
        result.indices.forEach { i -> result[i] = nextInt() }
        return result
    }
}

fun solveAll(reader: DataReader, writer: PrintWriter, solver: (DataReader) -> String) {
    val testsCount = reader.nextInt()

    for (test in 1..testsCount) {
        writer.println("Case #${test}: ${solver(reader)}")
    }

    writer.close()
}

fun solveAll(input: String = "input.txt", output: String = "output.txt", solver: (DataReader) -> String) {
    solveAll(
            DataReader.createFromFile(input),
            PrintWriter(output),
            solver
    )
}

infix fun Int.ceilDiv(n: Int) = (this / n) + (if (this % n == 0) 0 else 1)
@Suppress("NOTHING_TO_INLINE")
inline fun Int.sign() =
        when {
            this > 0 -> 1
            this < 0 -> -1
            else -> 0
        }

@Suppress("NOTHING_TO_INLINE")
inline fun Int.abs() = Math.abs(this)
