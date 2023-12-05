import structure.LinesPuzzle
import structure.ScannerPuzzle
import java.util.*
import kotlin.math.pow

class Day04Part2 : LinesPuzzle() {
    override fun solve(lines: List<String>): String {
        val wonCardsPerCard = mutableMapOf<Int, List<Int>>()

        lines.forEachIndexed { index, line ->
            val winningAndGivenNumbers = line.split(":")[1].split("|")
            val winningNumbers = winningAndGivenNumbers[0].getSeparatedNumbers()
            val givenNumbers = winningAndGivenNumbers[1].getSeparatedNumbers()
            val matchingNumbers = givenNumbers.filter { winningNumbers.contains(it) }

            var wonCards = mutableListOf<Int>()
            for (i in 1..matchingNumbers.size) {
                val wonCard = index + i
                wonCards.add(wonCard)
            }

            wonCardsPerCard[index] = wonCards
        }

        val reallyWonCopiesPerCard = mutableMapOf<Int, Int>()

        for (card in wonCardsPerCard.keys) {
            reallyWonCopiesPerCard[card] = reallyWonCopiesPerCard[card].increaseIfNotNullOrGet1()

            for (copyIndex in 0 until reallyWonCopiesPerCard[card]!!) {
                for (wonCard in wonCardsPerCard[card]!!) {
                    reallyWonCopiesPerCard[wonCard] = reallyWonCopiesPerCard[wonCard].increaseIfNotNullOrGet1()
                }
            }
        }

        return reallyWonCopiesPerCard.values.sum().toString()
    }

    private fun String.getSeparatedNumbers (): List<Int> =
        this.split(" ")
            .filter { it.isNotBlank() }
            .map { it.trim().toInt() }

    private fun Int?.increaseIfNotNullOrGet1(): Int {
        return if (this == null) {
            1
        } else {
            this + 1
        }
    }

    companion object {

    }
}