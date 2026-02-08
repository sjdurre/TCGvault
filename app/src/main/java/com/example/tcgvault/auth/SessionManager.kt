package com.example.tcgvault.auth

import android.content.Context

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setLoggedInUserId(userId: Long) {
        prefs.edit().putLong(KEY_USER_ID, userId).apply()
    }

    fun getLoggedInUserId(): Long {
        return prefs.getLong(KEY_USER_ID, -1L)
    }

    fun clearSession() {
        prefs.edit().remove(KEY_USER_ID).apply()
    }

    companion object {
        private const val PREF_NAME = "tcgvault_prefs"
        private const val KEY_USER_ID = "logged_in_user_id"
    }
}
