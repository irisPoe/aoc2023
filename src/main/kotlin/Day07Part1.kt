import structure.LinesPuzzle
import structure.ScannerPuzzle
import java.util.*

class Day07Part1 : LinesPuzzle() {
    enum class HandType {
        FIVE_OF_A_KIND,
        FOUR_OF_A_KIND,
        FULL_HOUSE,
        THREE_OF_A_KIND,
        TWO_PAIR,
        ONE_PAIR,
        HIGH_CARD
    }

    enum class CardType {
        Card2,
        Card3,
        Card4,
        Card5,
        Card6,
        Card7,
        Card8,
        Card9,
        CardT,
        CardJ,
        CardQ,
        CardK,
        CardA
    }

    override fun solve(lines: List<String>): String {
        val handsWithBids = mutableMapOf<Hand, Long>()

        for (line in lines) {
            val splitLine = line.split(" ")
            val hand = Hand(splitLine[0].map { CardType.valueOf("Card$it") })
            handsWithBids[hand] = splitLine[1].toLong()
        }

        val sortedHandsWithBids = handsWithBids.toList().sortedBy { it.first }
        var result = 0L
        sortedHandsWithBids.forEachIndexed { index, handWithBid ->
            result += handWithBid.second * (index+1)
        }

        return result.toString()
    }

    private class Hand(val hand: List<CardType>): Comparable<Hand> {
        val handType: HandType
        val cardOccurrences: List<CardOccurrence>

        init {
            this.cardOccurrences = getCardCountList()
            this.handType = getHandType(cardOccurrences)
        }

        fun winsAgainst(otherHand: Hand): Boolean {
            return if (otherHand.handType == this.handType) {
                var indexToCompare = 0

                while (this.hand[indexToCompare] == otherHand.hand[indexToCompare]) {
                    indexToCompare++
                }

                if (indexToCompare >= this.hand.size) {
                    throw Error("two equal hands given")
                }

                this.hand[indexToCompare] > otherHand.hand[indexToCompare]
            } else {
                this.handType < otherHand.handType
            }
        }

        private fun getCardCountList(): List<CardOccurrence> {
            val cardCountMap = mutableMapOf<CardType, Int>()

            for (cardType in hand) {
                if (cardCountMap.containsKey(cardType)) {
                    cardCountMap[cardType] = cardCountMap[cardType]!! + 1
                } else {
                    cardCountMap[cardType] = 1
                }
            }

            return cardCountMap.toList().map { CardOccurrence(it.first, it.second) }
        }

        private fun getHandType(cardOccurrences: List<CardOccurrence>): HandType {
            val sortedCardOccurrences = cardOccurrences.sortedByDescending { it.amount }

            return when (sortedCardOccurrences[0].amount) {
                5 -> HandType.FIVE_OF_A_KIND
                4 -> HandType.FOUR_OF_A_KIND
                3 -> {
                    if (sortedCardOccurrences[1].amount == 2) {
                        HandType.FULL_HOUSE
                    } else {
                        HandType.THREE_OF_A_KIND
                    }
                }
                2 -> {
                    if (sortedCardOccurrences[1].amount == 2) {
                        HandType.TWO_PAIR
                    } else {
                        HandType.ONE_PAIR
                    }
                }
                else -> HandType.HIGH_CARD
            }
        }

        override fun compareTo(other: Hand): Int =
            if (this.winsAgainst(other)) {
                1
            } else {
                -1
            }
    }

    private class CardOccurrence(val card: CardType, val amount: Int)

    companion object {

    }
}