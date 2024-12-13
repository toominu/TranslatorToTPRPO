package com.example.translator.request

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TranslationFavoriteManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("TranslationFavorites", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveTranslation(history: TextTranslated) {
        val favoriteList = getFavoriteList().toMutableList()
        favoriteList.add(history)

        // Ограничиваем количество записей до 20
        if (favoriteList.size > 20) {
            favoriteList.removeAt(0) // Удаляем самый старый элемент
        }

        val editor = sharedPreferences.edit()
        val json = gson.toJson(favoriteList)
        editor.putString("favorite", json)
        editor.apply()
    }

    fun getFavoriteList(): List<TextTranslated> {
        val json = sharedPreferences.getString("favorite", null)
        val type = object : TypeToken<List<TextTranslated>>() {}.type
        return if (json != null) {
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }
    fun deleteElemFavorite( index: Int){
        val favoriteList = getFavoriteList().toMutableList()
        val editor = sharedPreferences.edit()
        favoriteList.removeAt(index)
        editor.apply()
    }
    fun clearFavorite() {
        val editor = sharedPreferences.edit()
        editor.remove("favorite")
        editor.apply()
    }
}