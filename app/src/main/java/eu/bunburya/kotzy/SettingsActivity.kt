package eu.bunburya.kotzy

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        val userName = intent.getStringExtra(SETTINGS_USER_NAME)
        findViewById<EditText>(R.id.userNameEditText).setText(userName)

    }

    fun savePreferences(view: View) {
        val preferences = getSharedPreferences(getString(R.string.preference_file_key),
                            Context.MODE_PRIVATE)
        val userNameEditText = findViewById<EditText>(R.id.userNameEditText)
        val userName = userNameEditText.text.toString()
        val editor = preferences.edit()
        editor.putString("USER_NAME", userName)
        editor.apply()
        finish()
    }

}