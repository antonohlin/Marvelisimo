package com.example.marvelisimo.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
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

class ComicList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_character_list_)
        val characterToolbarLink = findViewById<TextView>(R.id.character_toolbar)
        characterToolbarLink.setOnClickListener{
            goToCharacters()
        }

        val viewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MarvelViewModel::class.java)
        viewModel.callMarvelObjects()

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = MarvelAdapter(this,viewModel.comicList) {
            val intent = Intent(this, DetailView::class.java)
            intent.putExtra(CharacterList.INTENT_PARCELABLE, it)
            startActivity(intent)
        }
    }

    fun goToCharacters() {
        val intent = Intent (this, CharacterList::class.java)
        startActivity(intent)
    }
}