package eu.bunburya.kotzy

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import eu.bunburya.kotzy.controllers.DiceController
import eu.bunburya.kotzy.controllers.DieState
import eu.bunburya.kotzy.controllers.GameController
import eu.bunburya.kotzy.game.components.Die
import eu.bunburya.kotzy.game.components.GameLauncher
import eu.bunburya.kotzy.game.components.GameManager
import kotlinx.android.synthetic.main.activity_single_player_game.*

const val CATEGORY_TEXT_SIZE: Float = 22f

class SinglePlayerGameActivity : AppCompatActivity() {

    val gameController: GameController by lazy { GameController(this) }
    val diceController: DiceController by lazy { DiceController(this, gameController) }
    val categoryToView: MutableMap<String, View> = mutableMapOf()
    lateinit var dieImages: Map<Pair<Int, DieState>, Bitmap>

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("GameActivity", "GameActivity created")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_player_game)
        dieImages = mapOf(
            Pair(1, DieState.UNSELECTED) to BitmapFactory.decodeResource(resources, R.drawable.dice_1),
            Pair(1, DieState.SELECTED) to BitmapFactory.decodeResource(resources, R.drawable.dice_1_invert),
            Pair(2, DieState.UNSELECTED) to BitmapFactory.decodeResource(resources, R.drawable.dice_2),
            Pair(2, DieState.SELECTED) to BitmapFactory.decodeResource(resources, R.drawable.dice_2_invert),
            Pair(3, DieState.UNSELECTED) to BitmapFactory.decodeResource(resources, R.drawable.dice_3),
            Pair(3, DieState.SELECTED) to BitmapFactory.decodeResource(resources, R.drawable.dice_3_invert),
            Pair(4, DieState.UNSELECTED) to BitmapFactory.decodeResource(resources, R.drawable.dice_4),
            Pair(4, DieState.SELECTED) to BitmapFactory.decodeResource(resources, R.drawable.dice_4_invert),
            Pair(5, DieState.UNSELECTED) to BitmapFactory.decodeResource(resources, R.drawable.dice_5),
            Pair(5, DieState.SELECTED) to BitmapFactory.decodeResource(resources, R.drawable.dice_5_invert),
            Pair(6, DieState.UNSELECTED) to BitmapFactory.decodeResource(resources, R.drawable.dice_6),
            Pair(6, DieState.SELECTED) to BitmapFactory.decodeResource(resources, R.drawable.dice_6_invert)
        )
        val rules = intent.getStringExtra(GAME_RULESET)
        val playerName = intent.getStringExtra(GAME_PLAYER_NAME)
        gameController.createGame(rules, listOf(playerName))
        drawDice()
        drawScoreCard()

    }

    fun drawScoreCard() {
        var categoryRow: TableRow
        var categoryDesc: TextView
        var categoryScore: TextView
        for (c in gameController.categoryList) {
            // FIXME:  Layout is broken
            categoryDesc = TextView(this).apply {
                text = c
                textSize = CATEGORY_TEXT_SIZE
                //layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                //    LinearLayout.LayoutParams.WRAP_CONTENT).apply { gravity = Gravity.START }
            }
            categoryScore = TextView(this).apply {
                text = (gameController.getScore(c)?.toString())?: ""
                textSize = CATEGORY_TEXT_SIZE
                //layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                //    LinearLayout.LayoutParams.WRAP_CONTENT).apply { gravity = Gravity.END }
            }
            categoryRow = TableRow(this).apply {
                addView(categoryDesc)
                addView(categoryScore)
                setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
                setOnClickListener(View.OnClickListener {
                    // FIXME:  Get this to work properly
                    categoryScore.text = gameController.setScore(c).toString()
                })
            }
            categoryToView[c] = categoryRow
            scoreCardTableLayout.addView(categoryRow)
        }
    }

    fun getDieImage(die: Die): Bitmap? = dieImages[Pair(die.value, diceController.getDieState(die))]

    fun drawDieButton(die: Die, button: ImageButton) {
        button.setImageBitmap(getDieImage(die))
        // TODO:  Android >= 16 uses setBackground instead; find a way to work with both.
        button.setBackgroundDrawable(null)
    }

    fun drawDice() {
        val buttonLayout = findViewById(R.id.gameDiceLayout) as LinearLayout
        var button: ImageButton
        for (die in diceController.dice) {
            button = ImageButton(this)
            drawDieButton(die, button)
            buttonLayout.addView(button)
        }
    }

}
