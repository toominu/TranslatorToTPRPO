package com.example.translator.request

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TranslationHistoryManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("TranslationHistory", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveTranslation(history: TextTranslated) {
        val historyList = getHistoryList().toMutableList()
        historyList.add(history)

        // Ограничиваем количество записей до 20
        if (historyList.size > 20) {
            historyList.removeAt(0) // Удаляем самый старый элемент
        }

        val editor = sharedPreferences.edit()
        val json = gson.toJson(historyList)
        editor.putString("history", json)
        editor.apply()
    }

    fun getHistoryList(): List<TextTranslated> {
        val json = sharedPreferences.getString("history", null)
        val type = object : TypeToken<List<TextTranslated>>() {}.type
        return if (json != null) {
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun clearHistory() {
        val editor = sharedPreferences.edit()
        editor.remove("history")
        editor.apply()
    }
}