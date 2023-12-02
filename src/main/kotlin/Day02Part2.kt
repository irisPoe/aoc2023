import structure.LinesPuzzle

class Day02Part2 : LinesPuzzle () {
    override fun solve(lines: List<String>): String {
        val regexForGameIds = Regex("Game \\d+:")
        var sumOfPowerOfMinimumCubeSets = 0

        for (line in lines) {
            val gameIdMatch = regexForGameIds.find(line)!!
            val gameSets = line.substring(gameIdMatch.range.last + 2).split(";")
            val minimumNumberOfCubesRequired = getMinimumNumberOfCubesRequired(gameSets)

            sumOfPowerOfMinimumCubeSets += minimumNumberOfCubesRequired.values
                .reduce{ number1, number2 -> number1 * number2 }
        }

        return sumOfPowerOfMinimumCubeSets.toString()
    }

    private fun getMinimumNumberOfCubesRequired(gameSets: List<String>): Map<String, Int> {
        val minimumNumberOfCubesRequired = mutableMapOf(
            "red" to 0,
            "blue" to 0,
            "green" to 0
        )

        gameSets.forEach { gameSet ->
            gameSet
                .split(",")
                .forEach { cubeAmount -> updateNumberOfMinimumCubes(cubeAmount, minimumNumberOfCubesRequired) }
        }

        return minimumNumberOfCubesRequired
    }

    private fun updateNumberOfMinimumCubes(cubeAmount: String, numberOfCubesNeeded: MutableMap<String, Int>) {
        val cubeAmountSplit = cubeAmount.trim().trimEnd(',').split(" ")
        val numberOfCubes = cubeAmountSplit[0].toInt()
        val colorOfCubes = cubeAmountSplit[1]

        if (numberOfCubesNeeded[colorOfCubes]!! < numberOfCubes) numberOfCubesNeeded[colorOfCubes] = numberOfCubes
    }

    companion object {

    }
}