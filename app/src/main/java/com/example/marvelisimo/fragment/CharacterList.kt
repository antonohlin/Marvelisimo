package com.example.marvelisimo.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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

        val noFavorites: TextView = findViewById<TextView>(R.id.no_favorites)
        val favorite = findViewById<ImageView>(R.id.favorite_icon)
        val comicsToolbarLink = findViewById<TextView>(R.id.comics_toolbar)
        val confirmSearch = findViewById<Button>(R.id.confirmSearchButton)
        val searchToolbarLink = findViewById<ImageView>(R.id.search)
        val searchField = findViewById<EditText>(R.id.SearchCharacterComic)
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val viewModel: MarvelViewModel by viewModels()
        val fp = FunctionProvider()

        favorite.setOnClickListener {
            val favorites = viewModel.loadFavorites()

            if (favorites.isEmpty()) {
                noFavorites.visibility = TextView.VISIBLE
            }

            recycler_view.layoutManager = LinearLayoutManager(this)
            recycler_view.setHasFixedSize(true)
            recycler_view.adapter = MarvelAdapter(this, favorites) {
                val intent = Intent(this, DetailView::class.java)
                intent.putExtra(INTENT_PARCELABLE, it)
                startActivity(intent)
            }
        }

        comicsToolbarLink.setOnClickListener {
            goToComics()
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
                val comicList = it.data.results.map { comic ->
                    MarvelItem(
                        comic.id,
                        comic.name,
                        comic.thumbnail.path,
                        comic.thumbnail.extension,
                        comic.description,
                        comic.urls[0].url
                    )
                }
                recycler_view.layoutManager = LinearLayoutManager(this)
                recycler_view.setHasFixedSize(true)
                recycler_view.adapter = MarvelAdapter(this, comicList) {
                    val intent = Intent(this, DetailView::class.java)
                    intent.putExtra(INTENT_PARCELABLE, it)
                    startActivity(intent)
                }
            })

        }



        if (fp.isOnline(this)) {
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
                recycler_view.layoutManager = LinearLayoutManager(this)
                recycler_view.setHasFixedSize(true)
                recycler_view.adapter = MarvelAdapter(this, comicList) {
                    val intent = Intent(this, DetailView::class.java)
                    intent.putExtra(INTENT_PARCELABLE, it)
                    startActivity(intent)
                }
            })

        } else {
            println("No internet lol")
            val faves = viewModel.loadFavorites()
            if (faves.isEmpty()) {
                findViewById<TextView>(R.id.no_connection).visibility = VISIBLE
            } else {
                recycler_view.layoutManager = LinearLayoutManager(this)
                recycler_view.setHasFixedSize(true)
                recycler_view.adapter = MarvelAdapter(this, faves) {
                    val intent = Intent(this, DetailView::class.java)
                    intent.putExtra(INTENT_PARCELABLE, it)
                    startActivity(intent)
                }
            }
        }
    }

    private fun goToComics() {
        val intent = Intent(this, ComicList::class.java)
        startActivity(intent)
    }

   
}

