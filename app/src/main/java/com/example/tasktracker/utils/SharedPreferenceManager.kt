package com.example.tasktracker.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

class SharedPreferenceManager(context: Context) {
    private var prefs = context.getSharedPreferences(
        Constants.PREFS_TOKEN_FILE,
        AppCompatActivity.MODE_PRIVATE
    )

    fun saveValue(key: String, value: String) {
        val editor = prefs.edit()
        editor.putString(key, value).apply()
    }

    fun getValue(key: String): String {
        return prefs.getString(key, "") ?: ""
    }

}