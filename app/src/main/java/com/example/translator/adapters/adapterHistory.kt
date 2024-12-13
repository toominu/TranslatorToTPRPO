package com.example.translator.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.translator.R
import com.example.translator.request.TextTranslated

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    private var historyList: List<TextTranslated> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_items, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = historyList[position]
        holder.bind(history)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    // Метод для обновления списка
    fun updateHistoryList(newHistoryList: List<TextTranslated>) {
        historyList = newHistoryList
        notifyDataSetChanged() // Уведомляем адаптер о том, что данные изменились
    }

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sourceLanguageTextView: TextView = itemView.findViewById(R.id.sourceLanguageH)
        private val sourceTextTextView: TextView = itemView.findViewById(R.id.sourceTextH)
        private val targetLanguageTextView: TextView = itemView.findViewById(R.id.targetLanguageH)
        private val targetTextTextView: TextView = itemView.findViewById(R.id.targetTextH)

        fun bind(history: TextTranslated) {
            sourceLanguageTextView.text = history.sourceLanguage
            sourceTextTextView.text = history.sourceText
            targetLanguageTextView.text = history.targetLanguage
            targetTextTextView.text = history.translatedText
        }
    }
}
