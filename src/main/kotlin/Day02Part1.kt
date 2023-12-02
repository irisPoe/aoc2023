import structure.LinesPuzzle

class Day02Part1 : LinesPuzzle () {
    override fun solve(lines: List<String>): String {
        val regexForGameIds = Regex("Game \\d+:")
        val numberOfCubesNeeded = mapOf(
            "red" to 12,
            "blue" to 14,
            "green" to 13
        )

        val matchingGameIds = mutableListOf<Int>()

        for (line in lines) {
            val gameIdMatch = regexForGameIds.find(line)!!
            val gameId = gameIdMatch.value.substring(5, gameIdMatch.value.length - 1).toInt()
            val gameSets = line.substring(gameIdMatch.range.last + 2).split(";")
            if (gameSetsFitNumberOfCubes(gameSets, numberOfCubesNeeded)) matchingGameIds.add(gameId)
        }

        return matchingGameIds.sum().toString()
    }

    private fun gameSetsFitNumberOfCubes(gameSets: List<String>, numberOfCubesNeeded: Map<String, Int>) =
        gameSets.all { gameSet ->
            gameSet
                .split(",")
                .all { cubeAmount -> cubeAmountFitsNeededCubes(cubeAmount, numberOfCubesNeeded) }
        }

    private fun cubeAmountFitsNeededCubes(cubeAmount: String, numberOfCubesNeeded: Map<String, Int>): Boolean {
        val cubeAmountSplit = cubeAmount.trim().trimEnd(',').split(" ")

        val numberOfCubes = cubeAmountSplit[0].toInt()
        val colorOfCubes = cubeAmountSplit[1]

        return numberOfCubesNeeded[colorOfCubes]!! >= numberOfCubes
    }

    companion object {

    }
}