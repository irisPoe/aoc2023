import structure.LinesPuzzle

class Day03Part2 : LinesPuzzle() {
    override fun solve(lines: List<String>): String {
        var mapOfNumbers = mutableMapOf<Int, Int>() // maps flattenedIndex to an occurring number
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

                    if (char == '*')
                        indexesOfSymbols.add(Pair(lineIndex, charIndex))
                }
            }

            if (readingNumber) {
                mapOfNumbers = setVisitedNumbersInMap(currentNumber, lineIndex, line.length, line.length, mapOfNumbers)
            }
        }

        for (symbolIndexes in indexesOfSymbols) {
            val adjacentNumbers =
                getAdjacentNumbers(symbolIndexes.second, symbolIndexes.first, lines[0].length, lines.size, mapOfNumbers)

            if (adjacentNumbers.size == 2) {
                sumOfAllAdjacentNumbers += adjacentNumbers[0] * adjacentNumbers[1]
            }
        }

        return sumOfAllAdjacentNumbers.toString()
    }

    private fun setVisitedNumbersInMap(currentNumberText: String, currentRowIndex: Int, currentColIndex: Int, numberOfColumns: Int, mapOfNumbers: MutableMap<Int, Int>): MutableMap<Int, Int> {
        val number = currentNumberText.toInt()

        for (numberIndex in currentColIndex - currentNumberText.length until currentColIndex) {
            val index = getFlattenedIndex(numberIndex, currentRowIndex, numberOfColumns)
            mapOfNumbers[index] = number
        }

        return mapOfNumbers
    }

    private fun getAdjacentNumbers(
        colIndex: Int,
        rowIndex: Int,
        numberOfColumns: Int,
        numberOfRows: Int,
        mapOfNumbers: Map<Int, Int>
    ): List<Int> {
        val visitedNumbers = mutableSetOf<Int>()

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

        return visitedNumbers.toList()
    }

    private fun getFlattenedIndex(colIndex: Int, rowIndex: Int, colLength: Int): Int =
        colIndex + rowIndex * colLength

    companion object {

    }
}