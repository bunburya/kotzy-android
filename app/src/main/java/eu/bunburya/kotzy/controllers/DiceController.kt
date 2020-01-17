package eu.bunburya.kotzy.controllers

import androidx.appcompat.app.AppCompatActivity
import eu.bunburya.kotzy.game.components.Dice
import eu.bunburya.kotzy.game.components.Die
import eu.bunburya.kotzy.game.components.GameManager

enum class DieState {
    UNSELECTED,
    SELECTED,
    INACTIVE
}

class DiceController(val activity: AppCompatActivity, var gameController: GameController) {

    val gameManager: GameManager get() = gameController.gameManager
    val dice: Dice get() = gameManager.dice

    fun getDieState(die: Die): DieState = when {
        gameManager.currentPlayer.rollsThisTurn >= gameManager.rules.maxRollsPerTurn
            -> DieState.INACTIVE
        die.isHeld -> DieState.SELECTED
        else -> DieState.UNSELECTED
    }

}