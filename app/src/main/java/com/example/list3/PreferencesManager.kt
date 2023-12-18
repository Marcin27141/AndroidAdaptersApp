package com.example.list3

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri

class PreferencesManager {
    private val DEFAULT_PREFS_NAME = "my_prefs"
    companion object {
        private var INSTANCE: PreferencesManager? = null
        fun getInstance(): PreferencesManager {
            if (INSTANCE == null)
                INSTANCE = PreferencesManager()
            return INSTANCE as PreferencesManager
        }
    }

    fun getTitleAndAuthor(activity: Activity) : Pair<String?, String?> {
        val data: SharedPreferences = activity.getSharedPreferences(DEFAULT_PREFS_NAME, Context.MODE_PRIVATE)
        return Pair(data.getString("invitationTitle", " "), data.getString("authorInfo", " "));
    }

    fun setHomeInfo(title: String, authorInfo: String, activity: Activity) {
        val data: SharedPreferences = activity.getSharedPreferences(DEFAULT_PREFS_NAME, Context.MODE_PRIVATE)
        val editor = data.edit()
        editor.putString("invitationTitle", title)
        editor.putString("authorInfo", authorInfo)
        editor.apply()
    }

    fun setHomeIcon(iconSrc: Int, activity: Activity) {
        val data: SharedPreferences = activity.getSharedPreferences(DEFAULT_PREFS_NAME, Context.MODE_PRIVATE)
        val editor = data.edit()
        editor.putInt("iconSrc", iconSrc)
        editor.apply()
    }

    fun setHomeImage(imageUri: Uri, activity: Activity) {
        val data: SharedPreferences = activity.getSharedPreferences(DEFAULT_PREFS_NAME, Context.MODE_PRIVATE)
        val editor = data.edit()
        editor.putString("imageUri", imageUri.toString())
        editor.apply()
    }

    fun getHomeIcon(activity: Activity) : Int {
        val data: SharedPreferences = activity.getSharedPreferences(DEFAULT_PREFS_NAME, Context.MODE_PRIVATE)
        return data.getInt("iconSrc", -1)
    }

    fun getHomeImageUri(activity: Activity) : Uri? {
        val data: SharedPreferences = activity.getSharedPreferences(DEFAULT_PREFS_NAME, Context.MODE_PRIVATE)
        val uriString = data.getString("imageUri", null)
        return if (uriString == null) null else Uri.parse(uriString)
    }
}