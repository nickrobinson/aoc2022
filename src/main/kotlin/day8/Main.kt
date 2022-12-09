package day8

import java.io.File
import java.io.InputStream
import java.lang.Integer.max

class Day08(input: List<String>) {
    private val treeMap: List<List<Int>> = createTreeMap(input)

    private fun createTreeMap(input: List<String>): List<List<Int>> {
        return input.map {
            it.toCharArray().map { it.digitToInt() }
        }
    }

    fun solvePartOne(): Int {
        val visibleMap = Array(treeMap.size) { Array(treeMap[0].size) { 0 } }

        // Mark boundary as visible
        for (row in treeMap.indices) {
            if (row == 0 || row == treeMap.size - 1) {
                visibleMap[row] = Array(treeMap[0].size) {1}
            }

            visibleMap[row][0] = 1
            visibleMap[row][treeMap[0].size-1] = 1
        }

        for (row in visibleMap) {
            println(row.contentToString())
        }

        // Scan rows
        println("SCANNING ROWS")
        for (row in 1..treeMap.size - 2) {
            var maxHeight = treeMap[row][0]

            for (col in 1 .. treeMap[0].size - 2) {
                println("$row, $col: Check $row, ${col -1 }")
                if (maxHeight < treeMap[row][col] && visibleMap[row][col-1] == 1) {
                    visibleMap[row][col] = 1
                }
                maxHeight = max(maxHeight, treeMap[row][col-1])
            }

            maxHeight = treeMap[row][treeMap[0].size - 1 ]
            for (col in treeMap[0].size - 2 downTo  1 ) {
                println("$row, $col: Check $row, ${col + 1 }")
                if (maxHeight < treeMap[row][col] && visibleMap[row][col+1] == 1) {
                    visibleMap[row][col] = 1
                }
                maxHeight = max(maxHeight, treeMap[row][col-1])
            }
        }

        // Scan cols
        println("SCANNING COLS")
        for (col in 1 .. treeMap[0].size - 2) {
            var maxHeight = treeMap[0][col]
            for (row in 1..treeMap.size - 2) {
                println("$row, $col: Check ${row -1 }, $col")
                if (maxHeight< treeMap[row][col] && visibleMap[row-1][col] == 1) {
                    visibleMap[row][col] = 1
                }
                maxHeight = max(maxHeight, treeMap[row-1][col])
            }

            maxHeight = treeMap[treeMap.size-1][col]
            for (row in treeMap.size - 2 downTo 1) {
                println("$row, $col: Check ${row + 1 }, $col")
                if (maxHeight < treeMap[row][col] && visibleMap[row+1][col] == 1) {
                    visibleMap[row][col] = 1
                }
                maxHeight = max(maxHeight, treeMap[row+1][col])
            }
        }

        for (row in visibleMap) {
            println(row.contentToString())
        }

        var total = 0
        for (element in visibleMap) {
            for (j in 0 until visibleMap[0].size) {
                total += element[j]
            }
        }
        return total
    }
}

fun main(args: Array<String>) {
    val inputStream: InputStream = File("src/main/kotlin/day8/test.txt").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val day8 = Day08(lineList)
    println("PART ONE: ${day8.solvePartOne()}")
}