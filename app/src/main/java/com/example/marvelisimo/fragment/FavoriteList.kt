package com.example.marvelisimo.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelisimo.R
import com.example.marvelisimo.adapter.MarvelAdapter
import com.example.marvelisimo.model.MarvelItem
import com.example.marvelisimo.viewmodel.FavoritesViewModel
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
        val viewModel: FavoritesViewModel by viewModels()
        val fp = FunctionProvider()

        if (fp.isOnline(this)) {

            viewModel.loadFavorites().observe(this, {
                val favorites = it.map { fav ->
                    MarvelItem(
                        fav.id,
                        fav.name,
                        fav.imageUrlBase,
                        fav.extension,
                        fav.description,
                        fav.url
                    )
                }

                if (favorites.isEmpty()) {
                    findViewById<TextView>(R.id.no_favorites).visibility = VISIBLE
                }else
                loadIntoRecycleView(this, favorites)
            })
        }

        comicsToolbarLink.setOnClickListener {
            goToComics()
        }

        characterToolbarLink.setOnClickListener(){
            goToCharacters()
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
        val intent = Intent(this, CharacterList::class.java)
        startActivity(intent)
    }
}

