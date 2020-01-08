package eu.bunburya.kotzy.game.rules

import eu.bunburya.kotzy.game.components.Dice

enum class YatzyLowerCategory(override val description: String): LowerCategory {
    ONE_PAIR("One Pair"),
    TWO_PAIR("Two Pairs"),
    THREE_KIND("Three of a Kind"),
    FOUR_KIND("Four of a Kind"),
    FULL_HOUSE("Full House"),
    SMALL_STRAIGHT("Small Straight"),
    LARGE_STRAIGHT("Large Straight"),
    YATZY("Yatzy"),
    CHANCE("Chance")
}

class YatzyRules: ScoringRules() {

    override val name = "Yatzy"
    override val lowerCategories = YatzyLowerCategory.values().toList()

    override val upperBonus = 50
    override val requiredUpperScoreForBonus = 63

    fun nKind(n: Int, dice: Dice): Int {
        val v = getNKind(n, dice)
        if (v != 0) return v * n
        else return 0

    }

    fun onePair(dice: Dice): Int = (getNKinds(2, dice).max()?:0) * 2

    fun twoPair(dice: Dice): Int {
        val nKinds = getNKinds(2, dice)
        if (nKinds.size == 2) return nKinds.sum() * 2
        else return 0
    }

    fun threeKind(dice: Dice): Int = this.nKind(3, dice)

    fun fourKind(dice: Dice): Int = this.nKind(4, dice)

    fun fullHouse(dice: Dice): Int = if (dice.valueCount.values.toSet() == setOf(2, 3)) dice.total else 0

    fun smallStraight(dice: Dice): Int = if (this.isNStraight(5, dice) && dice.values.min() == 1) dice.total else 0

    fun largeStraight(dice: Dice): Int = if (this.isNStraight(5, dice) && dice.values.min() == 2) dice.total else 0

    fun fiveKind(dice: Dice): Int = if (dice.valueCount.keys.size == 1) 50 else 0

    fun chance(dice: Dice): Int = dice.values.sum()

    override fun calculateScore(dice: Dice, category: Category): Int {
        when (category) {
            is UpperCategory -> return this.upperScore(category.ordinal+1, dice)
            YatzyLowerCategory.ONE_PAIR -> return this.onePair(dice)
            YatzyLowerCategory.TWO_PAIR -> return this.twoPair(dice)
            YatzyLowerCategory.THREE_KIND -> return this.threeKind(dice)
            YatzyLowerCategory.FOUR_KIND -> return this.fourKind(dice)
            YatzyLowerCategory.FULL_HOUSE -> return this.fullHouse(dice)
            YatzyLowerCategory.SMALL_STRAIGHT -> return this.smallStraight(dice)
            YatzyLowerCategory.LARGE_STRAIGHT -> return this.largeStraight(dice)
            YatzyLowerCategory.YATZY -> return this.fiveKind(dice)
            YatzyLowerCategory.CHANCE -> return this.chance(dice)
            else -> throw InvalidCategoryError("Invalid category: $category")
        }
    }

}