@file:OptIn(ExperimentalStdlibApi::class)

package day7

import java.io.File
import java.io.InputStream
import java.util.*

class Day07() {

    fun solvePartOne(commands: List<String>): Int {
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

        val rootDir = buildDirStructure(commands)
        println(calculateDirectorySizes(rootDir).map { it.value }.sum())
        return 0
    }

    private fun buildDirStructure(commands: List<String>): Directory {
        val rootDir = Directory("/", mutableListOf(), mutableListOf())
        var curDir = rootDir
        val directoryStack = Stack<Directory>()
        for (command in commands) {
            if (command[0] == '$') {
                // System Command
                println("SYSTEM COMMAND: $command")
                if (command == "$ cd ..") {
                    println("POPPING UP ONE DIRECTORY")
                    // Pop up one directory
                    curDir = directoryStack.pop()
                } else if (command == "$ cd /") {
                    curDir = rootDir
                } else if (command.startsWith("\$ cd")) {
                    println("CREATING DIRECTORY")
                    directoryStack.push(curDir)
                    val tmpDir = Directory(command.substring(5), mutableListOf(),  mutableListOf())
                    curDir.childDirs.add(tmpDir)
                    curDir = tmpDir
                }
            } else {
                if (command.startsWith("dir")) {
                    // Directory
                    println("DIRECTORY: ${command.substring(4)}")
                } else {
                    // File
                    println("FILE: $command")
                    val fileParts = command.split(" ")
                    curDir.files.add(File(fileParts[1], fileParts[0].toInt()))
                }
            }
        }

        return rootDir
    }

    private fun calculateDirectorySizes(rootDir: Directory): Map<String, Int> {
        // Run DFS approach to calculating directory sizes
        val visitedDirs = Stack<Directory>()
        val processingStack = Stack<Directory>()
        val postProcessingStack = Stack<Directory>()
        val dirSizeMap = mutableMapOf<String, Int>()

        processingStack.push(rootDir)
        postProcessingStack.push(rootDir)
        var curDir = rootDir

        while (processingStack.isNotEmpty()) {
            curDir = processingStack.pop()
            println("CURRENT DIRECTORY: ${curDir.name}")
            if (curDir.childDirs.isEmpty()) {
                curDir.size = curDir.files.sumOf { it.size }
            } else {
                for (dir in curDir.childDirs) {
                    if (!visitedDirs.contains(dir)) {
                        processingStack.push(dir)
                        visitedDirs.push(dir)
                    }
                }
            }
        }

        while (visitedDirs.isNotEmpty()) {
            curDir = visitedDirs.pop()
            curDir.size = curDir.files.sumOf { it.size } + curDir.childDirs.sumOf { it.size }
            dirSizeMap[curDir.name] = curDir.size
        }

        return dirSizeMap.filter { it.value <= 100000 }
    }


    private data class File(val name: String, val size: Int)
    private data class Directory(val name: String, val childDirs: MutableList<Directory>, val files: MutableList<File>, var size: Int = 0)
}

fun main(args: Array<String>) {
    val inputStream: InputStream = File("src/main/kotlin/day7/test.txt").inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val day7 = Day07()
    day7.solvePartOne(lineList)
}