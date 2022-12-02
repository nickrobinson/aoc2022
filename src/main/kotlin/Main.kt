import java.io.File
import java.io.InputStream
import java.util.*

fun main(args: Array<String>) {
    val inputStream: InputStream = File("/Users/nickrobinson/Development/java/aoc2022/src/main/kotlin/day1/input.txt").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val numElvesToConsider = 3
    val maxElfCalories: PriorityQueue<Int> = PriorityQueue<Int>()
    for (i in 1..numElvesToConsider) maxElfCalories.add(0)

    var curElfCalories = 0
    lineList.forEach {
        if (it.isNotBlank()) {
            curElfCalories += it.toInt()
        } else {
            maxElfCalories.add(curElfCalories)
            maxElfCalories.poll()
            curElfCalories = 0
        }
    }

    println("Max Elf Calories: $maxElfCalories")
    println("Max Elf Total Calories ${maxElfCalories.sum()}")
}