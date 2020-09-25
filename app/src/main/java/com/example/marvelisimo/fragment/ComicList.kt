package com.example.marvelisimo.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelisimo.R
import com.example.marvelisimo.adapter.MarvelAdapter
import com.example.marvelisimo.model.MarvelItem
import kotlinx.android.synthetic.main.layout_character_list_.*

class ComicList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_character_list_)
        val characterToolbarLink = findViewById<TextView>(R.id.character_toolbar)
        characterToolbarLink.setOnClickListener {
            goToCharacters()
        }

        val viewModel: MarvelViewModel by viewModels()
        viewModel.callMarvelObjects().observe(this, {
            val comicList = it.data.results.map { comic ->
                MarvelItem(comic.title, comic.thumbnail.path, comic.thumbnail.extension)
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

    private fun goToCharacters() {
        val intent = Intent(this, CharacterList::class.java)
        startActivity(intent)
    }
}