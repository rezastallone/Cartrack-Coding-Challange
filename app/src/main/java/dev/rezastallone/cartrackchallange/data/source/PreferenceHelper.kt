package dev.rezastallone.cartrackchallange.data.source

import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings.Global.getString
import dev.rezastallone.cartrackchallange.constant.SHARED_PREFERENCE_NAME

object PreferenceHelper {
    private fun getSharedPreference(context:Context): SharedPreferences {
        return context.getSharedPreferences(
                getString(context.contentResolver, SHARED_PREFERENCE_NAME), Context.MODE_PRIVATE)
    }

    fun putString(key: String, valueToPut: String, context: Context){
        val sharedPref = getSharedPreference(context)
        with (sharedPref.edit()) {
            putString(key, valueToPut)
            commit()
        }
    }

    fun getString(key: String, defaultValue: String, context: Context): String {
        val sharedPref = getSharedPreference(context)
        return sharedPref.getString(key, defaultValue)
    }
}