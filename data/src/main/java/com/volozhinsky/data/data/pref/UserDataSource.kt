package com.volozhinsky.data.data.pref

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val prefs: SharedPreferences
) {

    fun getUserCountry(): String = prefs.getString(COUNTRY_KEY, EMPTY_STRING).orEmpty()

    fun setUserCountry(country: String) = prefs.edit {
        putString(COUNTRY_KEY, country)
    }

    companion object {
        private const val COUNTRY_KEY = "COUNTRY_KEY"

        private const val EMPTY_STRING = ""
    }
}
