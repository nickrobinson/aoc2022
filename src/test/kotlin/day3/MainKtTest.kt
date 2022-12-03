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
    fun findSharedItem() {
        val testString = "vJrwpWtwJgWrhcsFMMfFFhFp"
        val splitRucksack = day3.splitRucksack(testString)
        assertEquals('p', day3.findSharedItem(splitRucksack[0], splitRucksack[1]))
    }

    @Test
    fun getPriority() {
        assertEquals(1, day3.getPriority('a'))
        assertEquals(27, day3.getPriority('A'))
    }
}