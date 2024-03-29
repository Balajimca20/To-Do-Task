package com.example.to_do_list.data.network

import android.content.Context
import androidx.core.content.edit

class PreferenceManager(context: Context) {

    private val appPreference = context.getSharedPreferences("AppData", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_APP_PLAYERS = "key.app.players"
    }

    fun clearAll() {
        appPreference.edit {
            clear()
        }
    }

    fun setPlayerData(data: String?) {
        appPreference.edit { putString(KEY_APP_PLAYERS, data) }
    }

    fun getPlayerData() = appPreference.getString(KEY_APP_PLAYERS, "")
}
