package eu.bunburya.kotzy.game.rules

import kotlin.reflect.full.createInstance

class InvalidRulesetName(msg: String): Exception(msg)

class RuleManager {

    // TODO:  Find better way to do this, perhaps using annotations.
    private val rulesets = hashMapOf(
            "Yahtzee" to YahtzeeRules::class,
            "Yatzy" to YatzyRules::class
    )

    val supportedRules: List<String> get() = rulesets.keys.sorted()

    fun getRules(name: String): ScoringRules {
        val rules = rulesets[name]
        if (rules == null) throw InvalidRulesetName("No rule set named $name found.")
        else return rules.createInstance()
    }

}