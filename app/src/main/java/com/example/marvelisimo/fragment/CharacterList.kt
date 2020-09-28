package com.example.marvelisimo.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelisimo.R
import com.example.marvelisimo.adapter.MarvelAdapter
import com.example.marvelisimo.api.MarvelRetrofit
import com.example.marvelisimo.model.CharacterDataWrapper
import com.example.marvelisimo.model.ComicDataWrapper
import com.example.marvelisimo.model.MarvelItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
        val comicsToolbarLink = findViewById<TextView>(R.id.comics_toolbar)
        val confirmSearch = findViewById<Button>(R.id.confirmSearchButton)
        val searchToolbarLink = findViewById<ImageView>(R.id.search)
        val searchField = findViewById<EditText>(R.id.SearchCharacterComic)
        comicsToolbarLink.setOnClickListener {
            goToComics()
        }
        searchToolbarLink.setOnClickListener {
            searchField.visibility = VISIBLE
            confirmSearch.visibility = VISIBLE
        }

        confirmSearch.setOnClickListener {
            Toast.makeText(this,findViewById<EditText>(R.id.SearchCharacterComic).text   ,Toast.LENGTH_SHORT).show()
            searchField.visibility = GONE
            confirmSearch.visibility = GONE
        }

        val fp = FunctionProvider()

        if (fp.isOnline(this)) {
            val viewModel: MarvelViewModel by viewModels()
            viewModel.callCharacters().observe(this, {
                val comicList = it.data.results.map { comic ->
                    MarvelItem(comic.name, comic.thumbnail.path, comic.thumbnail.extension)
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
            findViewById<TextView>(R.id.no_connection).visibility = VISIBLE
        }
    }

    private fun goToComics() {
        val intent = Intent (this, ComicList::class.java)
        startActivity(intent)
    }

}

