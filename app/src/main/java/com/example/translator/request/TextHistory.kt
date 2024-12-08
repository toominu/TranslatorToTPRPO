package com.example.translator.request

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

object TextHistory {
    val textHistory: ArrayList<TextTranslated> = ArrayList()
    fun getHistorySourceText( textHistory: TextTranslated): String {
        return textHistory.sourceText
    }
    fun getHistoryTargetText( textHistory: TextTranslated): String {
        return textHistory.targetText
    }
}


object TextHistoryManager {
    private const val PREFS_NAME = "text_history_prefs"
    private const val KEY_TEXT_HISTORY = "text_history"

    private val gson = Gson()

    // Сохранение данных
    fun saveTextHistory(context: Context, textHistory: ArrayList<TextTranslated>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val json = gson.toJson(textHistory)
        editor.putString(KEY_TEXT_HISTORY, json)
        editor.apply()
    }

    // Загрузка данных
    fun loadTextHistory(context: Context): ArrayList<TextTranslated> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_TEXT_HISTORY, null)
        val type = object : TypeToken<ArrayList<TextTranslated>>() {}.type
        return if (json != null) {
            gson.fromJson(json, type)
        } else {
            ArrayList() // Возвращаем пустой список, если данных нет
        }
    }
}