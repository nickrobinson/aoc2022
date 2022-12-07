@file:OptIn(ExperimentalStdlibApi::class)

package day7

import java.io.File
import java.io.InputStream
import java.util.*

class Day07() {
    fun solvePartOne(commands: List<String>): Int {
        val sizeMap = buildDirSizeMap(commands)
        return sizeMap.values.sumOf { if (it <= 100000) it else 0 }
    }

    fun solvePartTwo(commands: List<String>): Int? {
        val sizes = buildDirSizeMap(commands)
        val total = sizes.getValue("")
        return sizes.values.asSequence().filter { 70000000 - (total - it) >= 30000000 }.minOrNull()
    }

    private fun buildDirSizeMap(commands: List<String>): Map<String, Int> {
        val PATTERN = """[$] cd (.*)|(\d+).*""".toRegex()
        val sizeMap: Map<String, Int> = buildMap {
            put("", 0)
            var cwd = ""
            for (line in commands) {
                val match = PATTERN.matchEntire(line) ?: continue
                match.groups[1]?.value?.let { dir ->
                    cwd = when (dir) {
                        "/" -> ""
                        ".." -> cwd.substringBeforeLast('/', "")
                        else -> if (cwd.isEmpty()) dir else "$cwd/$dir"
                    }
                } ?: match.groups[2]?.value?.toIntOrNull()?.let { size ->
                    var dir = cwd
                    while (true) {
                        put(dir, getOrElse(dir) { 0 } + size)
                        if (dir.isEmpty()) break
                        dir = dir.substringBeforeLast('/', "")
                    }
                }
            }
        }

        return sizeMap
    }

}

fun main(args: Array<String>) {
    val inputStream: InputStream = File("src/main/kotlin/day7/test.txt").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val day7 = Day07()
    val partOneSolution = day7.solvePartOne(lineList)
    println("PART 1: $partOneSolution")

    val partTwoSolution = day7.solvePartTwo(lineList)
    println("PART 2: $partTwoSolution")
}