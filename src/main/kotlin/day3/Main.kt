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

fun findSharedItem(compartmentOne: String, compartmentTwo: String): Char {
    val sortedCompartmentOne = compartmentOne.toSortedSet().toCharArray()
    val sortedCompartmentTwo = compartmentTwo.toSortedSet().toCharArray()

    var i = 0
    var j = 0

    while (i < sortedCompartmentOne.size && j < sortedCompartmentTwo.size) {
        if (sortedCompartmentOne[i] == sortedCompartmentTwo[j]) {
            return sortedCompartmentOne[i]
        } else if (sortedCompartmentOne[i] < sortedCompartmentTwo[j]) {
            i++
        } else {
            j++
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
        getPriority(findSharedItem(splitRucksackParts[0], splitRucksackParts[1]))
    }
    println("PRIORITY SUM: $prioritySum")
}