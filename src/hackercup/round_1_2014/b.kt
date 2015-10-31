package hackercup.round_1_2014.b

import common.DataReader
import common.solveAll
import java.io.BufferedReader
import java.io.FileReader
import java.io.PrintWriter
import java.util.*

class TrieNode {
    val edges = hashMapOf<Char, TrieNode>()
}

fun main(args: Array<String>) {
    solveAll(solver = ::solveCase)
}

fun solveCase(inp: DataReader): String {
    val n = inp.nextInt()
    val root = TrieNode()
    
    return (1..n).map { 
        addWord(root, inp.nextToken()) 
    }.sum().toString()
}

fun addWord(root: TrieNode, word: String): Int {
    var current: TrieNode = root
    var result = 0
    
    while (result < word.length) {
        val next: TrieNode? = current.edges[word[result]]
        current = next ?: break
        result++
    }
    
    for (i in result..word.lastIndex) {
        val next = TrieNode()
        current.edges.put(word[i], next)
        current = next
    }
    
    return Math.min(result + 1, word.length)
}
