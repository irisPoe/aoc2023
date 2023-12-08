import structure.LinesPuzzle

class Day06Part2 : LinesPuzzle() {
    override fun solve(lines: List<String>): String {
        val raceDuration = lines[0].split(":")[1].replace(" ", "").toLong()
        val recordDistance = lines[1].split(":")[1].replace(" ", "").toLong()

        val race = Race(raceDuration, recordDistance)
        val halfRaceDuration = race.raceDuration / 2

        val lowerBound = findLowerBound(LongRange(1, halfRaceDuration), race)
        val upperBound = findUpperBound(LongRange(halfRaceDuration, race.raceDuration), race)

        return (upperBound - lowerBound + 1).toString()
    }

    private fun findLowerBound(buttonHoldTimeRange: LongRange, race: Race): Long {
        if (buttonHoldTimeRange.last - buttonHoldTimeRange.first <= 1) {
            return buttonHoldTimeRange.first
        }

        val halfRange = ((buttonHoldTimeRange.last - buttonHoldTimeRange.first) / 2) + buttonHoldTimeRange.first

        return if (race.wouldBeatRecordDistance(halfRange)) {
            findLowerBound(LongRange(buttonHoldTimeRange.first, halfRange), race)
        } else {
            findLowerBound(LongRange(halfRange + 1, buttonHoldTimeRange.last), race)
        }
    }

    private fun findUpperBound(buttonHoldTimeRange: LongRange, race: Race): Long {
        if (buttonHoldTimeRange.last - buttonHoldTimeRange.first <= 1) {
            return buttonHoldTimeRange.first
        }

        val halfRange = ((buttonHoldTimeRange.last - buttonHoldTimeRange.first) / 2) + buttonHoldTimeRange.first

        return if (race.wouldBeatRecordDistance(halfRange + 1)) {
            findUpperBound(LongRange(halfRange + 1, buttonHoldTimeRange.last), race)
        } else {
            findUpperBound(LongRange(buttonHoldTimeRange.first, halfRange), race)
        }
    }

    private class Race(val raceDuration: Long, val recordDistance: Long) {
        fun wouldBeatRecordDistance(buttonHoldTime: Long): Boolean =
            buttonHoldTime * (raceDuration - buttonHoldTime) > recordDistance
    }

    companion object {

    }
}