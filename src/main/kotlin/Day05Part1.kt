import structure.LinesPuzzle

class Day05Part1 : LinesPuzzle() {
    override fun solve(lines: List<String>): String {
        val seeds = lines[0].split(": ")[1].split(" ").map { it.toLong() }
        val isSeedMapped = mutableMapOf<Long, Boolean>()
        val valueToSeedsMap = mutableMapOf<Long, Long>()

        var newMap = mutableMapOf<Long, Long>()
        var valuesToRemoveFromMap = mutableListOf<Long>()

        seeds.forEach { isSeedMapped[it] = false }

        for (line in lines.drop(3).filter { it.isNotEmpty() }) {
            if (line.first().isDigit()) {
                val data = line.split(" ")
                val destRangeStart = data[0].toLong()
                val sourceRangeStart = data[1].toLong()
                val length = data[2].toLong()
                val diffFromDestinationToSource = sourceRangeStart - destRangeStart

                sourceRangeStart..sourceRangeStart + length
                val interestingSourceValues = seeds
                    .filter { !isSeedMapped[it]!! && it in sourceRangeStart..sourceRangeStart + length }
                    .toMutableList()
                interestingSourceValues.addAll(valueToSeedsMap.keys
                    .filter { it in sourceRangeStart..sourceRangeStart + length })

                for (sourceValue in interestingSourceValues) {
                    val destinationValue = sourceValue - diffFromDestinationToSource
                    if (valueToSeedsMap.containsKey(sourceValue)) { // if source already exists
                        valuesToRemoveFromMap.add(sourceValue)
                        val originalSeed = valueToSeedsMap[sourceValue]!!
                        newMap[destinationValue] = originalSeed
                    } else {
                        // initialize new value, seed has to be equal to current source because seed is not yet in map
                        newMap[destinationValue] = sourceValue
                    }

                }
            } else {
                valuesToRemoveFromMap.forEach { valueToSeedsMap.remove(it) }
                valueToSeedsMap.putAll(newMap)
                valueToSeedsMap.values.forEach { isSeedMapped[it] = true }
                newMap = mutableMapOf()
                valuesToRemoveFromMap = mutableListOf()
            }
        }

        valuesToRemoveFromMap.forEach { valueToSeedsMap.remove(it) }
        valueToSeedsMap.putAll(newMap)

        return valueToSeedsMap.keys.minOf { it }.toString()
    }

    companion object {

    }
}