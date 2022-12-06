package day4

import java.io.File
import java.io.InputStream

class Day04(input: List<String>) {

    private val ranges: List<Pair<IntRange,IntRange>> = input.map { it.asRanges() }

    fun solvePart1(): Int =
        ranges.count { it.first fullyOverlaps it.second || it.second fullyOverlaps it.first  }

    fun solvePart2(): Int =
        ranges.count { it.first overlaps it.second }

    private infix fun IntRange.fullyOverlaps(other: IntRange): Boolean =
        first <= other.first && last >= other.last

    private infix fun IntRange.overlaps(other: IntRange): Boolean =
        first <= other.last && other.first <= last

    private fun String.asRanges(): Pair<IntRange,IntRange> =
        substringBefore(",").asIntRange() to substringAfter(",").asIntRange()

    private fun String.asIntRange(): IntRange =
        substringBefore("-").toInt() .. substringAfter("-").toInt()
}

fun main(args: Array<String>) {
    val inputStream: InputStream = File("src/main/kotlin/day4/input.txt").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }
    val partOneSolution = Day04(lineList).solvePart1()
    println("PART 1: $partOneSolution")

    val partTwoSolution = Day04(lineList).solvePart2()
    println("PART 2: $partTwoSolution")
}