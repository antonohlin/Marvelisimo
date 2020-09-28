package com.example.marvelisimo.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

        val characterToolbarLink = findViewById<TextView>(R.id.character_toolbar)
        val confirmSearch = findViewById<Button>(R.id.confirmSearchButton)
        val searchToolbarLink = findViewById<ImageView>(R.id.search)
        val searchField = findViewById<EditText>(R.id.SearchCharacterComic)
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val viewModel: MarvelViewModel by viewModels()

        characterToolbarLink.setOnClickListener {
            goToCharacters()
        }

        searchToolbarLink.setOnClickListener {
            searchField.visibility = View.VISIBLE
            searchField.hint = "Search comics"
            confirmSearch.visibility = View.VISIBLE
            searchField.requestFocus()
            imm.showSoftInput(searchField, InputMethodManager.SHOW_IMPLICIT)
        }

        confirmSearch.setOnClickListener {
            searchField.visibility = View.GONE
            confirmSearch.visibility = View.GONE
            val searchValue = searchField.text.toString()
            viewModel.searchComics(searchValue).observe(this, {
                val comicList = it.data.results.map { comic ->
                    MarvelItem(comic.title, comic.thumbnail.path, comic.thumbnail.extension, comic.description, comic.urls[0].url)
                }
                Log.i("viewmodel", "observed")
                Log.i("comiclist", comicList.toString())
                recycler_view.layoutManager = LinearLayoutManager(this)
                recycler_view.setHasFixedSize(true)
                recycler_view.adapter = MarvelAdapter(this, comicList) {
                    val intent = Intent(this, DetailView::class.java)
                    intent.putExtra(CharacterList.INTENT_PARCELABLE, it)
                    startActivity(intent)
                }
            })

        }
        val fp = FunctionProvider()

        if (fp.isOnline(this)) {
            viewModel.callComics().observe(this, {
                val comicList = it.data.results.map { comic ->
                    MarvelItem(comic.title, comic.thumbnail.path, comic.thumbnail.extension, comic.description, comic.urls[0].url)
                }
                Log.i("viewmodel", "observed")
                Log.i("comiclist", comicList.toString())
                recycler_view.layoutManager = LinearLayoutManager(this)
                recycler_view.setHasFixedSize(true)
                recycler_view.adapter = MarvelAdapter(this, comicList) {
                    val intent = Intent(this, DetailView::class.java)
                    intent.putExtra(CharacterList.INTENT_PARCELABLE, it)
                    startActivity(intent)
                }
            })
        } else {
            println("No internet lol")
            findViewById<TextView>(R.id.no_connection).visibility = View.VISIBLE
        }
    }

    private fun goToCharacters() {
        val intent = Intent(this, CharacterList::class.java)
        startActivity(intent)
    }
}