package eu.bunburya.kotzy.controllers

import androidx.appcompat.app.AppCompatActivity
import eu.bunburya.kotzy.game.components.GameLauncher
import eu.bunburya.kotzy.game.components.GameManager
import eu.bunburya.kotzy.game.components.Player
import eu.bunburya.kotzy.game.rules.Category

class GameController (val activity: AppCompatActivity) {

    val gameLauncher = GameLauncher()

    lateinit var gameManager: GameManager
    lateinit var upperCategoryList: List<String>
    lateinit var lowerCategoryList: List<String>
    val categoryList: List<String> get() = upperCategoryList + lowerCategoryList
    lateinit var upperCategoryMap: Map<String, Category>
    lateinit var lowerCategoryMap: Map<String, Category>
    val categoryMap: Map<String, Category> get() = upperCategoryMap + lowerCategoryMap


    fun createGame(rules: String, playerNames: List<String>): GameManager {
        gameManager = gameLauncher.newGame(rules, playerNames)
        upperCategoryList = gameManager.rules.upperCategories.map { it.description }
        lowerCategoryList = gameManager.rules.lowerCategories.map { it.description }
        upperCategoryMap = gameManager.rules.upperCategories.map{ it.description to it }.toMap()
        lowerCategoryMap = gameManager.rules.lowerCategories.map{ it.description to it }.toMap()
        return gameManager
    }

    fun getPlayer(playerName: String): Player = gameManager.getPlayer(playerName)
    fun getPlayer(): Player = gameManager.currentPlayer

    fun getScore(category: String, playerName: String): Int? {
        val _category = categoryMap[category]!!
        return gameManager.getPlayer(playerName).scoreCard.getScore(_category)
    }

    fun getScore(category: String): Int? {
        val _category = categoryMap[category]!!
        return gameManager.currentPlayer.scoreCard.getScore(_category)
    }

    fun setScore(category: String): Int {
        val c = categoryMap[category]
        if (c == null) return 0
        else return gameManager.setScore(c)
    }
}