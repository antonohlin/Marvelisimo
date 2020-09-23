package com.example.marvelisimo.fragment

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.marvelisimo.api.MarvelRetrofit
import com.example.marvelisimo.model.ComicDataWrapper
import com.example.marvelisimo.model.MarvelItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MarvelViewModel : ViewModel() {
    val heroList = mutableListOf<ComicDataWrapper>()
    val comicList = generateComicList(heroList)

    @SuppressLint("CheckResult")
    fun callMarvelObjects() {
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
}