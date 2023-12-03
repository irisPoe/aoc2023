import structure.LinesPuzzle

class Day03Part1 : LinesPuzzle() {
    override fun solve(lines: List<String>): String {
        var mapOfNumbers = mutableMapOf<Int, VisitedNumber>() // maps flattenedIndex to an occurring number
        val indexesOfSymbols = mutableListOf<Pair<Int, Int>>() // rows, then cols
        var sumOfAllAdjacentNumbers = 0L

        for ((lineIndex, line) in lines.withIndex()) {
            var currentNumber = ""
            var readingNumber = false

            for ((charIndex, char) in line.withIndex()) {
                if (char.isDigit()) {
                    currentNumber += char
                    readingNumber = true
                } else {
                    if (readingNumber) {
                        mapOfNumbers = setVisitedNumbersInMap(currentNumber, lineIndex, charIndex, line.length, mapOfNumbers)

                        readingNumber = false
                        currentNumber = ""
                    }

                    if (!char.isWhitespace() && char != '.')
                        indexesOfSymbols.add(Pair(lineIndex, charIndex))
                }
            }

            if (readingNumber) {
                mapOfNumbers = setVisitedNumbersInMap(currentNumber, lineIndex, line.length, line.length, mapOfNumbers)
            }
        }

        for (symbolIndexes in indexesOfSymbols) {
            val notYetVisitedNumbers =
                getNotYetVisitedNumbers(symbolIndexes.second, symbolIndexes.first, lines[0].length, lines.size, mapOfNumbers)

            for (number in notYetVisitedNumbers) {
                number.visited = true
                sumOfAllAdjacentNumbers += number.number
            }
        }

        return sumOfAllAdjacentNumbers.toString()
    }

    private fun setVisitedNumbersInMap(currentNumberText: String, currentRowIndex: Int, currentColIndex: Int, numberOfColumns: Int, mapOfNumbers: MutableMap<Int, VisitedNumber>): MutableMap<Int, VisitedNumber> {
        val visitedNumber = VisitedNumber(currentNumberText.toInt())

        for (numberIndex in currentColIndex - currentNumberText.length until currentColIndex) {
            val index = getFlattenedIndex(numberIndex, currentRowIndex, numberOfColumns)
            mapOfNumbers[index] = visitedNumber
        }

        return mapOfNumbers
    }

    private fun getNotYetVisitedNumbers(
        colIndex: Int,
        rowIndex: Int,
        numberOfColumns: Int,
        numberOfRows: Int,
        mapOfNumbers: Map<Int, VisitedNumber>
    ): List<VisitedNumber> {
        val visitedNumbers = mutableSetOf<VisitedNumber>()

        for (rowDiff in -1..1) {
            val rowToCheck = rowIndex + rowDiff
            if (rowToCheck in 0 until numberOfRows) {
                for (colDiff in -1..1) {
                    val colToCheck = colIndex + colDiff
                    if (colToCheck in 0 until numberOfColumns) {
                        mapOfNumbers[getFlattenedIndex(colToCheck, rowToCheck, numberOfColumns)]
                            ?.also { visitedNumber -> visitedNumbers.add(visitedNumber)}
                    }
                }
            }
        }

        return visitedNumbers.filter { number -> !number.visited }
    }

    private fun getFlattenedIndex(colIndex: Int, rowIndex: Int, colLength: Int): Int =
        colIndex + rowIndex * colLength

    private class VisitedNumber (val number: Int) {
        var visited = false
    }

    companion object {

    }
}