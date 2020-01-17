package eu.bunburya.kotzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import eu.bunburya.kotzy.game.rules.RuleManager

const val GAME_RULESET = "eu.bunburya.kotzy.game.RULESET"

class SelectGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_game)

        val ruleManager = RuleManager()
        val layout = findViewById(R.id.gameTypeOptionsLayout) as LinearLayout
        val playerName = intent.getStringExtra(GAME_PLAYER_NAME)

        var b: Button

        for (r in ruleManager.supportedRules) {
            // each Button onClick launches a [SinglePlayer]GameActivity and sends the relevant
            // ruleset name
            b = Button(this)
            b.text = r
            //b.callOnClick()
            b.setOnClickListener(createButtonOnClickListener(r, playerName))
            layout.addView(b)
            Log.d("SelectGameActivity", "TESTING")
        }

    }

    fun createButtonOnClickListener(rules: String, playerName: String): View.OnClickListener {
        return View.OnClickListener {
            Log.d("OnClickListener", "in onClick function")
            val intent = Intent(this, SinglePlayerGameActivity::class.java).apply {
                putExtra(GAME_RULESET, rules)
                putExtra(GAME_PLAYER_NAME, playerName)
            }
            Log.d("OnClickListener", "intent created")
            startActivity(intent)
        }
    }
}
