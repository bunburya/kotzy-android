package eu.bunburya.kotzy.game.components

import eu.bunburya.kotzy.game.rules.Category
import eu.bunburya.kotzy.game.rules.InvalidCategoryError
import eu.bunburya.kotzy.game.rules.ScoringRules

class AlreadyScoredError(msg: String): Exception(msg)

class ScoreSection(private val categories: List<Category>): HashMap<Category, Int?>() {
    init {
        for (c in this.categories) this[c] = null
    }

    val totalScore: Int
        get() = this.values.sumBy{i -> i ?: 0}

}

class ScoreCard(val rules: ScoringRules) {

    // TODO:  Consider whether these should be private.
    // We can always get the categories themselves from ScoringRules.
    private val upperSection = ScoreSection(this.rules.upperCategories)
    private val lowerSection = ScoreSection(this.rules.lowerCategories)

    fun getScore(category: Category): Int? = this.getSection(category)[category]

    private fun getSection(category: Category): ScoreSection {
        if (this.upperSection.containsKey(category)) {
            return this.upperSection
        } else if (this.lowerSection.containsKey(category)) {
            return this.lowerSection
        } else {
            throw InvalidCategoryError("Invalid category: $category")
        }
    }

    fun setScore(category: Category, score: Int?) {
        /* Calls when a score has already been calculated, and sets the score in the appropriate section. */
        val section = this.getSection(category)
        if (section[category] != null)
            throw AlreadyScoredError("Player has already scored for $category")
        section[category] = score
    }

    fun setScore(category: Category, dice: Dice) {
        val score = rules.calculateScore(dice, category)
        setScore(category, score)
    }

    val totalUpperScore: Int get() = this.upperSection.totalScore // DOES NOT include upper bonus
    val totalLowerScore: Int get() = this.lowerSection.totalScore
    val totalScore: Int get() = this.upperSection.totalScore + this.lowerSection.totalScore + (rules.getUpperBonus(this)?:0)

}