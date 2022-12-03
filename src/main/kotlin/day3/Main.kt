package day3

import java.io.File
import java.io.InputStream


fun getPriority(item: Char): Int {
    if (!item.isLetter()) {
        return 0
    }

    return if (item.isLowerCase()) {
        val lowercaseOffset = 96
        item.code - lowercaseOffset
    } else {
        val uppercaseOffset = 38
        item.code - uppercaseOffset
    }
}

fun findSharedItem(groups: List<String>): Char {
    val sortedGroups = groups.map{it.toSortedSet()}
    val combinedArr = mutableListOf<Char>()
    for (i in sortedGroups) {
        combinedArr += i.toTypedArray()
    }
    // Sort result
    combinedArr.sort()
    var curChar = ' '
    var curCharCount = 1
    for (c in combinedArr) {
       if (c == curChar) {
           curCharCount++
       } else {
           curChar = c
           curCharCount = 1
       }

        if (curCharCount == groups.size) {
            return curChar
        }
    }

    return ' '
}

fun splitRucksack(rucksack: String): List<String> {
    return listOf(rucksack.substring(0, rucksack.length / 2), rucksack.substring(rucksack.length / 2))
}

fun main(args: Array<String>) {
    val inputStream: InputStream = File("src/main/kotlin/day3/input.txt").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val prioritySum = lineList.sumOf {
        val splitRucksackParts = splitRucksack(it)
        getPriority(findSharedItem(listOf(splitRucksackParts[0], splitRucksackParts[1])))
    }
    println("PRIORITY SUM PART 1: $prioritySum")

    var prioritySumPartTwo = 0
    for (i in 0..lineList.size - 2 step 3) {
        val sharedItem = findSharedItem(lineList.subList(i, i+3))
        prioritySumPartTwo += getPriority(sharedItem)
    }
    println("PRIORITY SUM PART 2: $prioritySumPartTwo")
}