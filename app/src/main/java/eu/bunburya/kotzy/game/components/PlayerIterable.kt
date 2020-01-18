package eu.bunburya.kotzy.game.components

class DuplicateNameError(msg: String): Exception(msg)

class PlayerIterator(val players: PlayerIterable): Iterator<Pair<String, Player>> {
    private var index = 0
    override fun hasNext(): Boolean = index < players.numPlayers
    override fun next(): Pair<String, Player> {
        val name = players.getPlayerNameByIndex(index)
        val player = players.getPlayerByIndex(index)
        index++
        return Pair(name, player!!)
    }
}

class PlayerIterable(val gameManager: GameManager, val initialPlayerNames: Iterable<String>): Iterable<Pair<String, Player>> {

    private val playerMap: HashMap<String, Player> = hashMapOf()
    val playerNames: MutableList<String> = mutableListOf()
    private var currentPlayerNameIndex = 0
    val currentPlayerName: String get() = playerNames[currentPlayerNameIndex]
    val currentPlayer: Player get() = playerMap[currentPlayerName]!!
    val numPlayers: Int get() = playerNames.size

    init {
        for (name in initialPlayerNames) addPlayer(name)
    }

    fun addPlayer(name: String) {
        if (name in playerMap) throw DuplicateNameError("$name is used more than once.")
        playerMap[name] = Player(gameManager, name)
        playerNames.add(name)
    }

    fun getPlayerNameByIndex(i: Int): String = playerNames[i]

    fun getPlayerByIndex(i:Int): Player? = playerMap[getPlayerNameByIndex(i)]

    fun getPlayerByName(name: String): Player? = playerMap[name]

    fun setCurrentPlayer(name: String) {
        currentPlayerNameIndex = playerNames.indexOf(name)
    }

    fun nextPlayer(): Pair<String, Player> {
        if (currentPlayerNameIndex < playerNames.size-1) currentPlayerNameIndex++
        else currentPlayerNameIndex = 0
        return Pair(currentPlayerName, currentPlayer)
    }

    override fun iterator(): PlayerIterator = PlayerIterator(this)
}