package eu.bunburya.kotzy.game.components

class Player(val game: Game, val name: String) {

    val scoreCard = ScoreCard(game.rules)
    var rolled = false

}