package com.example.translator.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.translator.databinding.HistoryItemsBinding
import com.example.translator.request.TextHistory.getHistoryTargetText
import com.example.translator.request.TextHistory.getHistorySourceText
import com.example.translator.request.TextHistory.textHistory


class adapterHistory (val context : Context,
) : RecyclerView.Adapter<adapterHistory.HistoryViewHolder>()  {

    class HistoryViewHolder(binding: HistoryItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        val sourceText = binding.sourceTextH
        val targetText = binding.targetTextH
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryViewHolder {
        val binding = HistoryItemsBinding.inflate(LayoutInflater.from(context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return textHistory.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.sourceText.text = getHistorySourceText(textHistory[position])
        holder.targetText.text = getHistoryTargetText(textHistory[position])
    }
}