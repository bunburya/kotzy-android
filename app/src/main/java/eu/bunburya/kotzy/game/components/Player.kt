package eu.bunburya.kotzy.game.components

class Player(val gameManager: GameManager, val name: String) {

    val scoreCard = ScoreCard(gameManager.rules)
    var rollsThisTurn = 0

}