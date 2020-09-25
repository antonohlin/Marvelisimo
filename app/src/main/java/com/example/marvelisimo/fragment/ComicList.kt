package com.example.marvelisimo.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelisimo.R
import com.example.marvelisimo.adapter.MarvelAdapter
import com.example.marvelisimo.model.ComicDataWrapper
import com.example.marvelisimo.model.MarvelItem
import kotlinx.android.synthetic.main.layout_character_list_.*
import kotlinx.android.synthetic.main.toolbar.*

class ComicList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_character_list_)
        val characterToolbarLink = findViewById<TextView>(R.id.character_toolbar)
        characterToolbarLink.setOnClickListener{
            goToCharacters()
        }

        val viewModel:MarvelViewModel by viewModels()
        val comicList = generateComicList(viewModel.callMarvelObjects())

        viewModel.callMarvelObjects().observe(this, Observer { it. })

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = MarvelAdapter(this, comicList) {
            val intent = Intent(this, DetailView::class.java)
            intent.putExtra(CharacterList.INTENT_PARCELABLE, it)
            startActivity(intent)
        }
    }
    fun generateComicList(heroList: MutableLiveData<List<ComicDataWrapper>>): List<MarvelItem> {

        val list = ArrayList<MarvelItem>()
        for (x in heroList[0].data.results.indices) {
            val item = MarvelItem(
                heroList[0].data.results[x].title,
                heroList[0].data.results[x].thumbnail.path,
                heroList[0].data.results[x].thumbnail.extension,
                //heroList[0].data.results[x].description
            )
            list += item
        }
        return list
    }

    fun goToCharacters() {
        val intent = Intent (this, CharacterList::class.java)
        startActivity(intent)
    }
}