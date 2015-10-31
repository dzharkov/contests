package hackercup.qualification_2014.c

import java.io.BufferedReader
import java.util.StringTokenizer
import java.io.FileReader
import java.io.PrintWriter
import java.util.ArrayDeque
import java.util.Queue
import java.util.concurrent.atomic.AtomicInteger

fun main(args: Array<String>) {
    solveAll(solver = ::solveCase)
}

fun solveCase(inp: DataReader) : String {
    val (n, m) = Pair(inp.nextInt(), inp.nextInt())
    val rotations = arrayOf(
            Pair(-1, 0), Pair(0, 1), Pair(1, 0), Pair(0, -1)            
    )
    
    val rotationsSymbols = charArrayOf('^', '>', 'v', '<')
    val symbolMap = Array(n) { inp.nextToken() }
    val map = Array(n) { Array(m) { BooleanArray(4) } }
    
    var startPoint: Pair<Int, Int>? = null
    var targetPoint: Pair<Int, Int>? = null
    
    fun inBounds(row: Int, column: Int) = row >= 0 && row < n && column >= 0 && column < m

    symbolMap.forEachIndexed { i, s ->
        s.forEachIndexed { j, c ->
            if (isPillar(c)) {
                map[i][j].fill(true)

                val currentLaserRotation = rotationsSymbols.indexOf(c)
                if (currentLaserRotation != -1) {
                    for (rShift in 0..3) {
                        var currentRow = i
                        var currentColumn = j
                        
                        while (true) {
                            currentRow += rotations[(rShift + currentLaserRotation) % 4].first
                            currentColumn += rotations[(rShift + currentLaserRotation) % 4].second
                            
                            if (!inBounds(currentRow, currentColumn) || isPillar(symbolMap[currentRow][currentColumn])) {
                                break
                            }
                            
                            map[currentRow][currentColumn][rShift] = true
                        } 
                    }
                }
            }
            else if (c == 'S') {
                assert(startPoint == null)
                startPoint = Pair(i, j)
            } 
            else if (c == 'G') {
                assert(targetPoint == null)
                targetPoint = Pair(i, j)
            }
        }
    }

    val distance = Array(n) { Array(m) { IntArray(4).let { it.fill(-1); it } } }
    distance[startPoint!!.first][startPoint!!.second][0] = 0    
    val queue: Queue<Triple<Int, Int, Int>> = ArrayDeque()
    queue.add(Triple(startPoint!!.first, startPoint!!.second, 0))
    
    while (queue.isNotEmpty()) {
        val current = queue.poll()
        val currentDist = distance[current.first][current.second][current.third]
        val nextRotation = (current.third + 1) % 4
        
        for (rotation in rotations) {
            val nextRow = current.first + rotation.first
            val nextColumn = current.second + rotation.second
            
            if (inBounds(nextRow, nextColumn) && 
                !map[nextRow][nextColumn][nextRotation] && distance[nextRow][nextColumn][nextRotation] == -1
            ) {
                queue.add(Triple(nextRow, nextColumn, nextRotation))
                distance[nextRow][nextColumn][nextRotation] = currentDist + 1
            }
        }
    }
    
    val result = distance[targetPoint!!.first][targetPoint!!.second].filter { it != -1 }.min() ?: -1
    
    return if (result != -1) result.toString() else "impossible"
}

private fun isPillar(c: Char): Boolean {
    return c != '.' && c != 'S' && c != 'G'
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




