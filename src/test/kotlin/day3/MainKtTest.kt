package day3

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class MainKtTest {

    @Test
    fun splitRucksack() {
        val testString = "vJrwpWtwJgWrhcsFMMfFFhFp"
        val splitRucksack = day3.splitRucksack(testString)
        assertEquals("vJrwpWtwJgWr", splitRucksack[0])
        assertEquals("hcsFMMfFFhFp", splitRucksack[1])
    }

    @Test
    fun getPriority() {
        assertEquals(1, day3.getPriority('a'))
        assertEquals(27, day3.getPriority('A'))
    }

    @Test
    fun findSharedItemList() {
        var testGroups = listOf("vJrwpWtwJgWrhcsFMMfFFhFp", "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL", "PmmdzqPrVvPwwTWBwg")
        var sharedItem = findSharedItem(testGroups)
        assertEquals('r', sharedItem)

        testGroups = listOf("wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn", "ttgJtRGJQctTZtZT", "CrZsJsPPZsGzwwsLwLmpwMDw")
        sharedItem = findSharedItem(testGroups)
        assertEquals('Z', sharedItem)
    }
}