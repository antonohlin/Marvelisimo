package com.example.marvelisimo.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
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

class ComicList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_character_list_)
        findViewById<TextView>(R.id.comics_toolbar).setTypeface(null, Typeface.BOLD);

        val noFavorites: TextView = findViewById<TextView>(R.id.no_favorites)
        val favorite = findViewById<ImageView>(R.id.favorite_icon)
        val characterToolbarLink = findViewById<TextView>(R.id.character_toolbar)
        val comicToolbarLink = findViewById<TextView>(R.id.comics_toolbar)
        val confirmSearch = findViewById<Button>(R.id.confirmSearchButton)
        val searchToolbarLink = findViewById<ImageView>(R.id.search)
        val searchField = findViewById<EditText>(R.id.SearchCharacterComic)
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val viewModel: MarvelViewModel by viewModels()
        val fp = FunctionProvider()

        characterToolbarLink.setOnClickListener {
            goToCharacters()
        }
        comicToolbarLink.setOnClickListener {
            noFavorites.visibility = TextView.GONE
            refreshComics()
        }

        searchToolbarLink.setOnClickListener {
            searchField.visibility = View.VISIBLE
            searchField.hint = "Search comics"
            confirmSearch.visibility = View.VISIBLE
            searchField.requestFocus()
            imm.showSoftInput(searchField, InputMethodManager.SHOW_IMPLICIT)
        }

        favorite.setOnClickListener{
            val favorites = viewModel.loadFavorites()

            if (favorites.isEmpty()){
                noFavorites.visibility = TextView.VISIBLE
            }
            loadIntoRecycleView(this, favorites)
        }

        confirmSearch.setOnClickListener {
            searchField.visibility = View.GONE
            confirmSearch.visibility = View.GONE
            val searchValue = searchField.text.toString()
            viewModel.searchComics(searchValue).observe(this, {
                val comicList = it.data.results.map { comic ->
                    MarvelItem(comic.id, comic.title, comic.thumbnail.path, comic.thumbnail.extension, comic.description, comic.urls[0].url)
                }
                loadIntoRecycleView(this, comicList)
            })
        }

        if (fp.isOnline(this)) {
            refreshComics()
        } else {
            println("No internet lol")
            findViewById<TextView>(R.id.no_connection).visibility = View.VISIBLE
        }
    }
    private fun refreshComics(){
        val viewModel: MarvelViewModel by viewModels()
        viewModel.callComics().observe(this, {
            val comicList = it.data.results.map { comic ->
                MarvelItem(comic.id, comic.title, comic.thumbnail.path, comic.thumbnail.extension, comic.description, comic.urls[0].url)
            }
            loadIntoRecycleView(this, comicList)
        })
    }

    private fun goToCharacters() {
        val intent = Intent(this, CharacterList::class.java)
        startActivity(intent)
    }

    private fun loadIntoRecycleView(context: Context, results: List<MarvelItem>) {
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = MarvelAdapter(context, results) {
            val intent = Intent(this, DetailView::class.java)
            intent.putExtra(CharacterList.INTENT_PARCELABLE, it)
            startActivity(intent)
        }
    }

}