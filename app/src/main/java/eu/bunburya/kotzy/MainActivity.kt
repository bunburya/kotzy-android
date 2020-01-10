package eu.bunburya.kotzy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

const val SETTINGS_USER_NAME = "eu.bunburya.kotzy.settings.USER_NAME"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun newGame(view: View) {
        val intent = Intent(this, SelectGameActivity::class.java)
        startActivity(intent)
    }

    fun preferences(view: View) {
        val preferences = getSharedPreferences(getString(R.string.preference_file_key),
                            Context.MODE_PRIVATE)
        val userName = preferences.getString("USER_NAME",
            getResources().getString(R.string.default_user_name))
        val intent = Intent(this, SettingsActivity::class.java).apply {
            putExtra(SETTINGS_USER_NAME, userName)
        }
        startActivity(intent)
    }
}
