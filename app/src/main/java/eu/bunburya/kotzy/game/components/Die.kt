package eu.bunburya.kotzy.game.components

class DieError(message: String): Exception(message)

class Die(private val initialValue: Int? = null, val maxValue: Int = 6) {

    private val range = 1..maxValue
    var value = if (this.initialValue == null) this.range.random() else this.initialValue
    init {
        if (this.value > this.maxValue) throw DieError("Staring value $this.value greater than max value $this.maxValue")
    }

    var isHeld: Boolean = false

    fun roll(): Int {
        this.value = this.range.random()
        return this.value
    }

}