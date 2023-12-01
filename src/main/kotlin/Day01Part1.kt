import structure.ScannerPuzzle
import java.util.*

class Day01Part1 : ScannerPuzzle() {
    override fun solve(scanner: Scanner): String {
        var sumOfCalibrationValues = 0

        while (scanner.hasNextLine()) {
            val line = scanner.nextLine()
            sumOfCalibrationValues +=
                (line.first { char -> char.isDigit() }.toString() +
                line.last { char -> char.isDigit() }.toString())
                    .toInt()
        }

        return sumOfCalibrationValues.toString()
    }

    companion object {

    }
}