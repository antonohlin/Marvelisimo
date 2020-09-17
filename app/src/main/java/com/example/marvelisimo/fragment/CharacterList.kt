package com.example.marvelisimo.fragment

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelisimo.R
import com.example.marvelisimo.adapter.MarvelAdapter
import com.example.marvelisimo.api.MarvelRetrofit
import com.example.marvelisimo.model.CharacterDataWrapper
import com.example.marvelisimo.model.MarvelItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_character_list_.*

class CharacterList : AppCompatActivity() {

    companion object {
        val INTENT_PARCELABLE = "MARVEL_ITEM"
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_character_list_)

        val heroList = mutableListOf<CharacterDataWrapper>()

        MarvelRetrofit.marvelService.getAllCharacters(limit = 100)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result, err ->
                if (err?.message != null) Log.d("__", "Error getAllCharacters " + err.message)
                else {
                    heroList.add(result)
                    //Log.d("___", "I got a CharacterDataWrapper $result")
                    //println(heroList.size)
                }

                val characterList = generateCharacterList(heroList)

                recycler_view.layoutManager = LinearLayoutManager(this)
                recycler_view.setHasFixedSize(true)
                recycler_view.adapter = MarvelAdapter(this, characterList) {
                    val intent = Intent(this, DetailView::class.java)
                    intent.putExtra(INTENT_PARCELABLE, it)
                    startActivity(intent)
                }
            }
    }

    private fun generateCharacterList(heroList: List<CharacterDataWrapper>): List<MarvelItem> {

        val list = ArrayList<MarvelItem>()

        for (x in heroList[0].data.results.indices) {
            val item = MarvelItem(
                heroList[0].data.results[x].name,
                heroList[0].data.results[x].thumbnail.path,
                heroList[0].data.results[x].thumbnail.extension,
                heroList[0].data.results[x].description
            )
            list += item
        }
        return list
    }
}
