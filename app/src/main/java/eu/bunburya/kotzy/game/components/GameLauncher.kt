package eu.bunburya.kotzy.game.components

import eu.bunburya.kotzy.game.rules.RuleManager

class GameLauncher {

    private val ruleManager = RuleManager()

    val supportedRules: List<String> get() = ruleManager.supportedRules

    fun newGame(rules: String): GameManager {
        return GameManager(ruleManager.getRules(rules), listOf("Alan"))
    }

}