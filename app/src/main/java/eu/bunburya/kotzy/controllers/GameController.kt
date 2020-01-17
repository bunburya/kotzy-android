package eu.bunburya.kotzy.controllers

import androidx.appcompat.app.AppCompatActivity
import eu.bunburya.kotzy.game.components.GameLauncher
import eu.bunburya.kotzy.game.components.GameManager

class GameController (val activity: AppCompatActivity) {

    val gameLauncher = GameLauncher()
    lateinit var gameManager: GameManager

    fun createGame(rules: String, playerNames: List<String>): GameManager {
        gameManager = gameLauncher.newGame(rules, playerNames)
        return gameManager
    }

}