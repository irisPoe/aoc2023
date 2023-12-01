import structure.ScannerPuzzle
import java.util.*

class Day01Part2 : ScannerPuzzle() {
    private val textDigitsToNumbers = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    override fun solve(scanner: Scanner): String {
        var sumOfCalibrationValues = 0
        val regexForNumbers = Regex("""^(one|two|three|four|five|six|seven|eight|nine|1|2|3|4|5|6|7|8|9)""")

        while (scanner.hasNextLine()) {
            val line = scanner.nextLine()
            val allDigitMatches = mutableListOf<MatchResult>()

            line.forEachIndexed { index, _ -> regexForNumbers
                .find(line.substring(index))
                ?.also { result -> allDigitMatches.add(result) }
            }

            val firstDigit = allDigitMatches.first().value.getNumericDigit()
            val lastDigit = allDigitMatches.last().value.getNumericDigit()

            sumOfCalibrationValues += (firstDigit + lastDigit).toInt()
        }

        return sumOfCalibrationValues.toString()
    }

    private fun String.getNumericDigit(): String {
        return if (this.length > 1) textDigitsToNumbers[this].toString() else this
    }

    companion object {

    }
}