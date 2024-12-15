package com.example.translator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.translator.adapters.HistoryAdapter
import com.example.translator.adapters.FavoriteAdapter
import com.example.translator.request.TextTranslated
import com.example.translator.request.TranslationFavoriteManager
import com.example.translator.request.TranslationHistoryManager

class FavoriteFragment : Fragment() {
    private lateinit var favoriteManager: TranslationFavoriteManager
    private lateinit var adapter: FavoriteAdapter
    private var favoriteList: List<TextTranslated> = emptyList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteManager = TranslationFavoriteManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.favoriteView)

        adapter = FavoriteAdapter(favoriteList, favoriteManager, requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        loadFavorite()

        return view
    }

    private fun loadFavorite() {
        val favoriteList = favoriteManager.getFavoriteList()
        adapter.updateFavoriteList(favoriteList)
    }
}