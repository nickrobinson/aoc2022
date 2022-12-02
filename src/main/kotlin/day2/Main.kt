package day2

import java.io.File
import java.io.InputStream

class RpsGame(_opponentMove: String, _myMove: String) {
    private val choiceMap =
        mapOf("A" to "ROCK", "B" to "PAPER", "C" to "SCISSORS", "X" to "ROCK", "Y" to "PAPER", "Z" to "SCISSORS")
    private val choiceValueMap = mapOf("ROCK" to 1, "PAPER" to 2, "SCISSORS" to 3)
    private val opponentMove: String
    private val myMove: String = _myMove

    init {
        opponentMove = choiceMap[_opponentMove].toString()
    }

    fun getGameScoreWithMove(): Int {
        var score: Int = choiceValueMap[myMove]!!
        val myMoveChoice = choiceMap[myMove]
        if (opponentMove == myMoveChoice) {
            score += 3
        } else if ((opponentMove == "SCISSORS" && myMoveChoice == "ROCK") || (opponentMove == "PAPER" && myMoveChoice == "SCISSORS") || (opponentMove == "ROCK" && myMoveChoice == "PAPER")) {
            // I win the round
            score += 6
        }
        return score
    }

    // Calculate game score given whether to lose, win, or draw game
    fun getGameScoreWithStrategy(): Int {
        var score = 0

        // Take care of calculating score bump due to draw/win
        when (myMove) {
            // DRAW
            "Y" -> {
                score += 3
                // We make the same move as the opponent if we draw
                score += choiceValueMap[opponentMove]!!
            }
            // WIN
            "Z" -> {
                score += 6
                when (opponentMove) {
                    "ROCK" -> {
                        score += choiceValueMap["PAPER"]!!
                    }
                    "SCISSORS" -> {
                        score += choiceValueMap["ROCK"]!!
                    }
                    "PAPER" -> {
                        score += choiceValueMap["SCISSORS"]!!
                    }
                }
            }
            // LOSE
            "X" -> {
                when (opponentMove) {
                    "ROCK" -> {
                        score += choiceValueMap["SCISSORS"]!!
                    }
                    "SCISSORS" -> {
                        score += choiceValueMap["PAPER"]!!
                    }
                    "PAPER" -> {
                        score += choiceValueMap["ROCK"]!!
                    }
                }
            }
        }

        // Figure out what move we made and score
        return score
    }
}

fun main(args: Array<String>) {
    val inputStream: InputStream =
        File("/Users/nickrobinson/Development/java/aoc2022/src/main/kotlin/day2/input.txt").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    var runningGameSum = 0
    lineList.forEach {
        val strategy = it.split(' ')
        runningGameSum += RpsGame(strategy[0], strategy[1]).getGameScoreWithStrategy()
        println("TOTAL SCORE: $runningGameSum")
    }
}