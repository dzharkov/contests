package hackercup.qualification_2014.b
import java.io.BufferedReader
import java.util.StringTokenizer
import java.io.FileReader
import java.io.PrintWriter
import java.util.concurrent.FutureTask

fun main(args: Array<String>) {
    solveAll(solver = ::solveCase)
}

fun solveCase(inp: DataReader) : String {
    val shouldBe = inp.nextIntTriple()
    val n = inp.nextInt()
    
    val allTriples = Array(n) { inp.nextIntTriple() }
    val result = (0..(1 shl n) - 1).any { mask ->
        allTriples.withIndex().
            filter { mask.isBitSet(it.index) }.
            map { it.value }.
            fold(Triple(0,0,0)) { accumulator, next ->
                Triple(accumulator.first + next.first, accumulator.second + next.second, accumulator.third + next.third)
            } == shouldBe
    }    
    
    return if (result) "yes" else "no"
}

fun Int.isBitSet(b: Int) = this and (1 shl b) > 0

class DataReader(private val reader: BufferedReader) {
    var st : StringTokenizer? = null
    companion object {
        fun createFromFile(name: String) = DataReader(BufferedReader(FileReader(name)))
    }

    fun next() : String? {
        while (st == null || !st!!.hasMoreTokens()) {
            val s = reader.readLine()
            if (s == null)
                return null
            st = StringTokenizer(s)
        }

        return st?.nextToken()
    }

    fun nextToken() : String {
        return next()!!
    }

    fun nextInt() = nextToken().toInt()
    fun nextLong() = nextToken().toLong()
    fun readIntArray(n: Int) : IntArray {
        val result = IntArray(n)
        result.indices.forEach { i -> result[i] = nextInt() }
        return result
    }
    
    fun nextIntTriple() = Triple(nextInt(), nextInt(), nextInt())
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


