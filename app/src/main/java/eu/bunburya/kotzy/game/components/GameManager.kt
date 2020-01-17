package eu.bunburya.kotzy.game.components

import eu.bunburya.kotzy.game.rules.ScoringRules

class GameInProgressError(msg: String): Exception(msg)

class GameManager (val rules: ScoringRules, private val initialPlayerNames: Iterable<String>) {

    private val players = PlayerIterable(this, initialPlayerNames)
    private var inProgress = false
    val dice = Dice(rules.numDice, rules.diceSides)

    // I considered using delegation here but it didn't seem to make sense to make a separate interface for
    // PlayerIterable that would only ever be implemented once, so we just create forwarding methods as necessary.
    // TODO:  Consider whether delegation to a PlayerIterable interface would actually be a better choice.

    val playerNames: List<String> get() = players.playerNames
    val currentPlayerName: String get() = players.currentPlayerName
    val currentPlayer: Player get() = players.currentPlayer
    val numPlayers: Int get() = players.numPlayers
    fun nextPlayer(): Pair<String, Player> {
        val result = players.nextPlayer()
        currentPlayer.rollsThisTurn = 0
        return result
    }

    fun addPlayer(name: String) {
        if (inProgress) throw GameInProgressError("Cannot add players when game is in progress.")
        players.addPlayer(name)
    }

    fun startGame() {
        inProgress = true
    }

}