package eu.bunburya.kotzy.game.rules

import eu.bunburya.kotzy.game.components.Dice
import eu.bunburya.kotzy.game.components.ScoreCard

enum class YahtzeeLowerCategory: LowerCategory {
    THREE_KIND {
        override val description = "Three of a Kind"
    },
    FOUR_KIND {
        override val description = "Four of a Kind"
    },
    FULL_HOUSE {
        override val description = "Full House"
    },
    SHORT_STRAIGHT {
        override val description = "Short Straight"
    },
    LONG_STRAIGHT {
        override val description = "Long Straight"
    },
    FIVE_KIND {
        override val description = "Five of a Kind"
    },
    CHANCE {
        override val description = "Chance"
    }
}

enum class JokerRule {
    NONE,           // No joker rule
    FORCED_UPPER,   // Force play in upper section if available, allow play in lower section if not
    FREE_CHOICE,    // Player completely free to play in any category
    FORCED_LOWER    // Player free to place in lower section, cannot place in upper section
}

class YahtzeeBonus: Bonus(100, YahtzeeLowerCategory.FIVE_KIND) {
    override fun match(rules: ScoringRules, dice: Dice, scoreCard: ScoreCard): Boolean {
        val fiveKind = YahtzeeLowerCategory.FIVE_KIND
        val yahtzeeScore1 = scoreCard.getScore(fiveKind)?:0
        println("Existing yahtzee score: $yahtzeeScore1")
        val yahtzeeScore2 = rules.calculateScore(dice, fiveKind)
        println("New yahtzee score: $yahtzeeScore2")
        return (scoreCard.getScore(fiveKind)?:0 > 0) && (rules.calculateScore(dice, fiveKind) > 0)
    }
}

class YahtzeeRules: ScoringRules() {

    override val name = "Yahtzee"
    override val lowerCategories = YahtzeeLowerCategory.values().toList()
    override val requiredUpperScoreForBonus = 63
    override val upperBonus = 35

    val jokerRule = JokerRule.FORCED_UPPER
    val extraFiveKindBonus = 100

    fun nKind(n: Int, dice: Dice): Int = if (getNKind(n, dice) != 0) dice.total else 0

    fun threeKind(dice: Dice): Int = this.nKind(3, dice)

    fun fourKind(dice: Dice): Int = this.nKind(4, dice)

    fun fullHouse(dice: Dice): Int = if (dice.valueCount.values.toSet() == setOf(2, 3)) 25 else 0

    fun shortStraight(dice: Dice): Int = if (this.isNStraight(4, dice)) 30 else 0

    fun longStraight(dice: Dice): Int = if (this.isNStraight(5, dice)) 40 else 0

    fun fiveKind(dice: Dice): Int = if (dice.valueCount.keys.size == 1) 50 else 0

    fun chance(dice: Dice): Int = dice.total

    override fun calculateScore(dice: Dice, category: Category): Int {
        when (category) {
            is UpperCategory -> return this.upperScore(category.ordinal+1, dice)
            YahtzeeLowerCategory.THREE_KIND -> return this.threeKind(dice)
            YahtzeeLowerCategory.FOUR_KIND -> return this.fourKind(dice)
            YahtzeeLowerCategory.FULL_HOUSE -> return this.fullHouse(dice)
            YahtzeeLowerCategory.SHORT_STRAIGHT -> return this.shortStraight(dice)
            YahtzeeLowerCategory.LONG_STRAIGHT -> return this.longStraight(dice)
            YahtzeeLowerCategory.FIVE_KIND -> return this.fiveKind(dice)
            YahtzeeLowerCategory.CHANCE -> return this.chance(dice)
            else -> throw InvalidCategoryError("Invalid category: $category")
        }
    }

    override val specialActions = listOf(
            YahtzeeBonus()
    )

}