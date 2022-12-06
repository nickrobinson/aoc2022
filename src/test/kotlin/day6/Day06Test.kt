package day6

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day06Test {

    @Test
    fun solvePartOne() {
        val day6 = Day06("bvwbjplbgvbhsrlpgdmjqwftvncz")
        assertEquals(5, day6.solve(4))
    }

    @Test
    fun solvePartTwo() {
        val day6 = Day06("bvwbjplbgvbhsrlpgdmjqwftvncz")
        assertEquals(23, day6.solve(14))
    }
}