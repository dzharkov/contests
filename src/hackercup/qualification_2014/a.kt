package hackercup.qualification_2014.a
import java.io.BufferedReader
import java.util.StringTokenizer
import java.io.FileReader
import java.io.PrintWriter

fun main(args: Array<String>) {
    solveAll(solver = ::solveCase)
}

fun solveCase(inp: DataReader) : String {
    val n = inp.nextToken()
    val allVariants = 
            n.indices.flatMap { i -> (i+1..n.lastIndex).map { j -> Pair(i,j) } }.
            map {
                (
                    n.substring(0, it.first) + 
                    n[it.second] + 
                    n.substring(it.first + 1, it.second) + 
                    n[it.first] + 
                    n.substring(it.second + 1)
                ).toInt()
            }.filter { it.toString().length == n.length } + n.toInt()
    
    return "${allVariants.min()} ${allVariants.max()}"
}

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


