package com.example.marvelisimo.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelisimo.R
import com.example.marvelisimo.adapter.MarvelAdapter
import com.example.marvelisimo.model.MarvelItem
import com.example.marvelisimo.viewmodel.MarvelViewModel
import kotlinx.android.synthetic.main.layout_character_list_.*

class FavoriteList : AppCompatActivity() {

    companion object {
        const val INTENT_PARCELABLE = "MARVEL_ITEM"
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_character_list_)
        setSupportActionBar(findViewById(R.id.toolbar))

        val comicsToolbarLink = findViewById<TextView>(R.id.comics_toolbar)
        val characterToolbarLink = findViewById<TextView>(R.id.character_toolbar)
        val confirmSearch = findViewById<Button>(R.id.confirmSearchButton)
        val searchToolbarLink = findViewById<ImageView>(R.id.search)
        val searchField = findViewById<EditText>(R.id.SearchCharacterComic)
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val viewModel: MarvelViewModel by viewModels()
        
        comicsToolbarLink.setOnClickListener {
            goToComics()
        }

        characterToolbarLink.setOnClickListener(){
            goToCharacters()
        }

        searchToolbarLink.setOnClickListener {
            searchField.visibility = VISIBLE
            searchField.hint = "Search characters"
            confirmSearch.visibility = VISIBLE
            searchField.requestFocus()
            imm.showSoftInput(searchField, InputMethodManager.SHOW_IMPLICIT)
        }

            val faves = viewModel.loadFavorites()
            if (faves.isEmpty()) {
                findViewById<TextView>(R.id.no_connection).visibility = VISIBLE
            } else {
                loadIntoRecycleView(this, faves)
            }
        }

    private fun loadIntoRecycleView(context: Context, results: List<MarvelItem>) {
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = MarvelAdapter(context, results) {
            val intent = Intent(this, DetailView::class.java)
            intent.putExtra(INTENT_PARCELABLE, it)
            startActivity(intent)
        }

    }

    private fun goToComics() {
        val intent = Intent(this, ComicList::class.java)
        startActivity(intent)
    }

    private fun goToCharacters() {
        val intent = Intent(this, ComicList::class.java)
        startActivity(intent)
    }
}

