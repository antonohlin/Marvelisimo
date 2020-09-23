package com.example.marvelisimo.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelisimo.R
import com.example.marvelisimo.adapter.MarvelAdapter
import com.example.marvelisimo.api.MarvelRetrofit
import com.example.marvelisimo.model.ComicDataWrapper
import com.example.marvelisimo.model.MarvelItem
import com.example.marvelisimo.model.MarvelItemDataWrapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_character_list_.*

class ComicList : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_character_list_)

        val heroList = mutableListOf<MarvelItemDataWrapper>()

        val characterToolbarLink = findViewById<TextView>(R.id.character_toolbar)
        characterToolbarLink.setOnClickListener{
            goToCharacters()

        }

        MarvelRetrofit.marvelService.getAllComics(limit = 100)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result, err ->
                if (err?.message != null) Log.d("__", "Error getAllCharacters " + err.message)
                else {
                    heroList.add(result)
                    Log.d("___", "I got a ComicDataWrapper $result")
                    println(heroList.size)
                }

                val comicList = generateComicList(heroList)

                recycler_view.layoutManager = LinearLayoutManager(this)
                recycler_view.setHasFixedSize(true)
                recycler_view.adapter = MarvelAdapter(this, comicList) {
                    val intent = Intent(this, DetailView::class.java)
                    intent.putExtra(CharacterList.INTENT_PARCELABLE, it)
                    startActivity(intent)
                }
            }
    }

    private fun generateComicList(heroList: List<ComicDataWrapper>): List<MarvelItem> {

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