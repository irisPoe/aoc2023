import structure.LinesPuzzle

class Day06Part1 : LinesPuzzle() {
    override fun solve(lines: List<String>): String {
        val raceDurations = lines[0].split(":")[1].split(" ").filter { it.isNotBlank() }.map { it.trim().toLong() }
        val recordDistances = lines[1].split(":")[1].split(" ").filter { it.isNotBlank() }.map { it.trim().toLong() }

        val races = raceDurations.mapIndexed { index, duration -> Race(duration, recordDistances[index])}
        var productOfWinningPossibilitiesPerRace = 1L

        for (race in races) {
            val halfRaceDuration = race.raceDuration / 2
            val winningPossibilities = mutableSetOf<Long>()

            for (buttonHoldTime in halfRaceDuration downTo(1)) {
                if (race.wouldBeatRecordDistance(buttonHoldTime)) {
                    winningPossibilities.add(buttonHoldTime)
                } else {
                    break
                }
            }

            for (buttonHoldTime in halfRaceDuration + 1 until race.raceDuration) {
                if (race.wouldBeatRecordDistance(buttonHoldTime)) {
                    winningPossibilities.add(buttonHoldTime)
                } else {
                    break
                }
            }

            productOfWinningPossibilitiesPerRace *= winningPossibilities.size
        }

        return productOfWinningPossibilitiesPerRace.toString()
    }

    private class Race(val raceDuration: Long, val recordDistance: Long) {
        fun wouldBeatRecordDistance(buttonHoldTime: Long): Boolean =
            buttonHoldTime * (raceDuration - buttonHoldTime) > recordDistance
    }

    companion object {

    }
}