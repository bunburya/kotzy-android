package eu.bunburya.kotzy.controllers

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import eu.bunburya.kotzy.SinglePlayerGameActivity
import eu.bunburya.kotzy.game.components.Dice
import eu.bunburya.kotzy.game.components.Die
import eu.bunburya.kotzy.game.components.GameManager

enum class DieState {
    UNSELECTED,
    SELECTED,
    INACTIVE
}

class DiceController(val activity: SinglePlayerGameActivity, var gameController: GameController) {

    val gameManager: GameManager get() = gameController.gameManager
    val dice: Dice get() = gameManager.dice
    private val viewToDie = mutableMapOf<View, Die>()
    private val dieToView = mutableMapOf<Die, View>()

    fun getDieState(die: Die): DieState = when {
        gameManager.currentPlayer.rollsThisTurn == 0 -> DieState.INACTIVE
        gameManager.currentPlayer.rollsThisTurn >= gameManager.rules.maxRollsPerTurn
            -> DieState.INACTIVE
        die.isHeld -> DieState.SELECTED
        else -> DieState.UNSELECTED
    }
    fun getDieState(button: ImageButton): DieState {
        return getDieState(viewToDie[button]!!)
    }

    fun getDiePair(button: ImageButton): Pair<Int, DieState> {
        val die = viewToDie[button]!!
        return Pair(die.value, getDieState(die))
    }

    fun registerDie(view: View, die: Die) {
        viewToDie[view] = die
        dieToView[die] = view
    }

    fun holdDie(button: ImageButton) {
        Log.d("DiceController", "holdDie called")
        val die = viewToDie[button]!!
        if (getDieState(die) != DieState.INACTIVE) {
            die.isHeld = !die.isHeld
            activity.drawDieButton(button)
        }
    }

}