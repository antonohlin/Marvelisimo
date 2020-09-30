package com.example.marvelisimo.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
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

class CharacterList : AppCompatActivity() {

    companion object {
        const val INTENT_PARCELABLE = "MARVEL_ITEM"
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_character_list_)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<TextView>(R.id.character_toolbar).setTypeface(null, Typeface.BOLD);

        val noFavorites: TextView = findViewById(R.id.no_favorites)
        val favorite = findViewById<ImageView>(R.id.favorite_icon)
        val comicsToolbarLink = findViewById<TextView>(R.id.comics_toolbar)
        val charactersToolbarLink = findViewById<TextView>(R.id.character_toolbar)
        val confirmSearch = findViewById<Button>(R.id.confirmSearchButton)
        val searchToolbarLink = findViewById<ImageView>(R.id.search)
        val searchField = findViewById<EditText>(R.id.SearchCharacterComic)
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val viewModel: MarvelViewModel by viewModels()
        val fp = FunctionProvider()

        favorite.setOnClickListener {
            goToFavorites()
        }

        comicsToolbarLink.setOnClickListener {
            goToComics()
        }
        charactersToolbarLink.setOnClickListener {
            noFavorites.visibility = TextView.GONE
            refreshCharacters()
        }

        searchToolbarLink.setOnClickListener {
            searchField.visibility = VISIBLE
            searchField.hint = "Search characters"
            confirmSearch.visibility = VISIBLE
            searchField.requestFocus()
            imm.showSoftInput(searchField, InputMethodManager.SHOW_IMPLICIT)
        }

        confirmSearch.setOnClickListener {
            searchField.visibility = View.GONE
            confirmSearch.visibility = View.GONE
            val searchValue = searchField.text.toString()
            viewModel.searchCharacters(searchValue).observe(this, {
                val comicList = it.data.results.map { char ->
                    MarvelItem(
                        char.id,
                        char.name,
                        char.thumbnail.path,
                        char.thumbnail.extension,
                        char.description,
                        char.urls[0].url
                    )
                }
                loadIntoRecycleView(this, comicList)
            })

        }

        if (fp.isOnline(this)) {
           refreshCharacters()
        } else {
            val faves = viewModel.loadFavorites()
            if (faves.isEmpty()) {
                findViewById<TextView>(R.id.no_favorites).visibility = VISIBLE
            } else {
                loadIntoRecycleView(this, faves)
            }
        }
    }

    private fun refreshCharacters(){
        val viewModel: MarvelViewModel by viewModels()
        viewModel.callCharacters().observe(this, {
            val comicList = it.data.results.map { comic ->
                MarvelItem(
                    comic.id,
                    comic.name,
                    comic.thumbnail.path,
                    comic.thumbnail.extension,
                    comic.description,
                    comic.urls[1].url
                )
            }

            loadIntoRecycleView(this, comicList)
        })
    }


    private fun goToComics() {
        val intent = Intent(this, ComicList::class.java)
        startActivity(intent)
    }

    private fun goToFavorites() {
        val intent = Intent(this, FavoriteList::class.java)
        startActivity(intent)
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

}

