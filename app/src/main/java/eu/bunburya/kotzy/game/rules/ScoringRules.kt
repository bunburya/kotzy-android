package eu.bunburya.kotzy.game.rules

import eu.bunburya.kotzy.game.components.Dice
import eu.bunburya.kotzy.game.components.ScoreCard

interface Category {
    val ordinal: Int
    val description: String
}

interface UpperCategory: Category
interface LowerCategory: Category

class InvalidCategoryError(msg: String): Exception(msg)
class UpperBonusError(msg: String): Exception(msg)

enum class CommonUpperCategory(override val description: String): UpperCategory {
    // Every variant appears to support the same upper section (and related scoring rules), so better to just define
    // a common upper section and scoring function here.
    ONES("Ones"),
    TWOS("Twos"),
    THREES("Threes"),
    FOURS("Fours"),
    FIVES("Fives"),
    SIXES("Sixes")
}

enum class BadCategory: Category {
    // For testing purposes.
    BAD_CATEGORY {
        override val description = "Bad category"
    }
}

interface SpecialAction {
    fun match(rules: ScoringRules, dice: Dice, scoreCard: ScoreCard): Boolean
}

abstract class ScoreAgain(val extraScoreRules: ScoringRules): SpecialAction {
    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is ScoreAgain) return false
        return extraScoreRules == other.extraScoreRules
    }
}
abstract class Bonus(val bonus: Int, val placeInCategory: Category?): SpecialAction {
    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is Bonus) return false
        return (bonus == other.bonus) && (placeInCategory == other.placeInCategory)
    }
}
abstract class Win: SpecialAction {
    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is Win) return false
        return true
    }
}

abstract class AutoScore(val category: Category, val customScore: Int? = null, val endTurn: Boolean): SpecialAction {
    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is AutoScore) return false
        return (category == other.category) && (customScore == other.customScore) && (endTurn == other.endTurn)
    }
}

abstract class ScoringRules {
    // Base class for scoring rules.  Defines the required interface and also provides some sane defaults and helper
    // functions based on those rules which are common to most game variants.

    abstract val name: String
    open val upperCategories: List<UpperCategory> = CommonUpperCategory.values().toList()
    abstract val lowerCategories: List<LowerCategory>
    open val numDice: Int = 5
    open val diceSides: Int = 6
    open val numRolls = 3
    open val categories get() = upperCategories + lowerCategories
    open val upperBonus: Int? = null
    open val requiredUpperScoreForBonus: Int? = null
    open fun getUpperBonus(scoreCard: ScoreCard): Int? {
        if ((upperBonus == null) || (requiredUpperScoreForBonus == null)) {
            if ((upperBonus != null) || (requiredUpperScoreForBonus != null)) {
                throw UpperBonusError("upperBonus and requiredUpperScoreForBonus must either both be null or both be Int.")
            }
            return null
        }
        if (scoreCard.totalUpperScore >= requiredUpperScoreForBonus!!) return upperBonus
        else return 0
    }
    open fun scoreBonus(scoreCard: ScoreCard): Int? = null /* Return a bonus based on player's
                                                                                  score; return if no bonus available. */
    open val specialActions: List<SpecialAction> = emptyList()


    open fun upperScore(n: Int, dice: Dice): Int {
        /* Calculates a score in the upper section.
        n is an Int corresponding to the category being scored (eg, n=1 means you want to
        score in the ones category, etc).

        Default behaviour is to return the sum of values of dice matching n.
         */
        val category = this.upperCategories[n-1]
        return dice.filter{it.value == n}.sumBy{it.value}
    }

    open fun getNKind(n: Int, dice: Dice): Int {
        // Generic function to find an "n of a kind".  Value of found n-of-a-kind, or 0 if n-of-a-kind found.
        for ((value, count) in dice.valueCount) {
            if (count >= n) return value
        }
        return 0
    }

    open fun getNKinds(n: Int, dice: Dice): Collection<Int> {
        val nKinds = mutableListOf<Int>()
        for ((value, count) in dice.valueCount) {
            if (count >= n) nKinds.add(value)
        }
        return nKinds
    }

    open fun isNStraight(n: Int, dice: Dice): Boolean {
        // Generic function to find an n-long straight.  Returns true or false.
        val sortedVals = dice.values.sorted()
        var streak = 1
        for (i in 1 until dice.size) {
            if (sortedVals[i] - sortedVals[i-1] == 1) {
                streak++
                if (streak >= n) return true
            } else {
                streak = 1
            }
        }
        return false
    }

    abstract fun calculateScore(dice: Dice, category: Category): Int

    open fun getMatchingActions(dice: Dice, scoreCard: ScoreCard): List<SpecialAction> {
        return specialActions.filter { it.match(this, dice, scoreCard) }
    }

}
