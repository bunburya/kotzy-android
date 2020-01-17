package eu.bunburya.kotzy

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

const val SETTINGS_USER_NAME = "eu.bunburya.kotzy.settings.USER_NAME"
const val GAME_PLAYER_NAME = "eu.bunburya.kotzy.game.PLAYER_NAME"


class MainActivity : AppCompatActivity() {

    var preferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = getSharedPreferences(getString(R.string.preference_file_key),
            Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun newSinglePlayerGame(view: View) {
        val userName = preferences?.getString("USER_NAME",
            getResources().getString(R.string.default_user_name))
        val intent = Intent(this, SelectGameActivity::class.java).apply {
            putExtra(GAME_PLAYER_NAME, userName)
        }
        startActivity(intent)
    }

    fun preferences(view: View) {
        val userName = preferences?.getString("USER_NAME",
            getResources().getString(R.string.default_user_name))
        val intent = Intent(this, PreferencesActivity::class.java).apply {
            putExtra(SETTINGS_USER_NAME, userName)
        }
        startActivity(intent)
    }
}
