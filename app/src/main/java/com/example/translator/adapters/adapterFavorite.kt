package com.example.translator.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.translator.R
import com.example.translator.request.TextTranslated

class adapterFavorite  : RecyclerView.Adapter<adapterFavorite.FavoriteViewHolder>() {
    private var favoriteList: List<TextTranslated> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_items, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = favoriteList[position]
        holder.bind(favorite)

        holder.deleteFromFBtn.setOnClickListener {
            removeItem(favorite)        }
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    // Метод для обновления списка
    fun updateFavoriteList(newFavoriteList: List<TextTranslated>) {
        favoriteList = newFavoriteList
        notifyDataSetChanged() // Уведомляем адаптер о том, что данные изменились
    }
    fun removeItem(favorite: TextTranslated) {
        val updatedList = favoriteList.toMutableList()
        updatedList.remove(favorite)
        favoriteList = updatedList
        notifyDataSetChanged() // Уведомляем адаптер о том, что данные изменились
    }
    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sourceLanguageTextView: TextView = itemView.findViewById(R.id.sourceLanguageF)
        private val sourceTextTextView: TextView = itemView.findViewById(R.id.sourceTextF)
        private val targetLanguageTextView: TextView = itemView.findViewById(R.id.targetLanguageF)
        private val targetTextTextView: TextView = itemView.findViewById(R.id.targetTextF)
        var deleteFromFBtn : ImageButton = itemView.findViewById(R.id.deleteBttnFav)

        fun bind(favorite: TextTranslated) {
            sourceLanguageTextView.text = favorite.sourceLanguage
            sourceTextTextView.text = favorite.sourceText
            targetLanguageTextView.text = favorite.targetLanguage
            targetTextTextView.text = favorite.translatedText
        }
    }
}