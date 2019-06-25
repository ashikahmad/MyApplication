package api

import android.content.Context
import android.content.SharedPreferences

class UserDefaults(context: Context) {
    val PREFS_FILENAME = "com.ashikahmad.MyApplication.prefs"
    val ACCESS_TOKEN = "access_token"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0);

    var accessToken: String
        get() = prefs.getString(ACCESS_TOKEN, "") ?: ""
        set(value) = prefs.edit().putString(ACCESS_TOKEN, value).apply()
}
