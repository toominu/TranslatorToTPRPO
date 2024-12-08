package com.example.translator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.translator.adapters.adapterHistory
import com.example.translator.databinding.FragmentHistoryBinding
import com.example.translator.request.TextHistoryManager


class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var historyAdapter : adapterHistory
    private lateinit var historyRecycleView : RecyclerView
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
//        val context = view?.context

        historyRecycleView = view?.findViewById(R.id.historyView)!!
        historyAdapter = adapterHistory(requireContext())
        historyRecycleView.layoutManager = LinearLayoutManager(requireContext())
        historyRecycleView.adapter = historyAdapter
//        val loadedTextHistory = context?.let { TextHistoryManager.loadTextHistory(it) }
        return view
    }


}