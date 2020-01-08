package eu.bunburya.kotzy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout

import eu.bunburya.kotzy.game.rules.RuleManager

class SelectGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_game)

        val ruleManager = RuleManager()
        val layout = findViewById(R.id.gameTypeOptionsLayout) as LinearLayout

        var b: Button

        for (r in ruleManager.supportedRules) {
            b = Button(this)
            b.text = r
            layout.addView(b)
        }

    }
}
