import structure.LinesPuzzle
import structure.ScannerPuzzle
import java.util.*
import kotlin.math.pow

class Day04Part1 : LinesPuzzle() {
    override fun solve(lines: List<String>): String {
        return lines.sumOf { line ->
            val winningAndGivenNumbers = line.split(":")[1].split("|")
            val winningNumbers = winningAndGivenNumbers[0].trim().split(" ")
                .filter { it.isNotBlank() }
                .map { it.trim().toInt() }
            val givenNumbers = winningAndGivenNumbers[1].trim().split((" "))
                .filter { it.isNotBlank() }
                .map { it.trim().toInt() }
            val matchingNumbers = givenNumbers.filter { winningNumbers.contains(it) }

            if (matchingNumbers.size == 1) {
                1
            } else {
                2.0.pow(matchingNumbers.size - 1).toInt()
            }
        }.toString()
    }

    companion object {

    }
}