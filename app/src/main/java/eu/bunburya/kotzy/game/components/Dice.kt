package eu.bunburya.kotzy.game.components

class DiceError(msg: String): Exception(msg)

class Dice(private val numDice: Int = 5, private val maxValue: Int = 6,
           private val initVals: List<Int>? = null): ArrayList<Die>() {

    var numRolls = 0
    init {
        if (initVals != null) {
            if (initVals.size != numDice) {
                throw DiceError("Asked for $numDice dice; gave $initVals.length initial values.")
            }
            for (i in initVals) this.add(Die(i, maxValue))
        } else {
            repeat(this.numDice, {i -> this.add(Die(i + 1, maxValue))})
        }

    }

    val valueCount: HashMap<Int, Int> get() {
        var countMap = HashMap<Int, Int>()
        for (d in this.toSet()) {
            var v = d.value
            countMap[d.value] = this.count{ it.value == v }
        }
        return countMap
    }

    val values: List<Int> get() = this.map{ it.value }
    val total: Int get() = this.sumBy{ it.value }

    fun roll() {
        for (d in this) {
            if (!d.isHeld) d.roll()
        }
        this.numRolls++
    }

    fun hold(indices: Iterable<Int>) {
        for (i in indices) {
            this[i].isHeld = true
        }
    }
}