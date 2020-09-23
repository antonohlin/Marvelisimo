package com.example.marvelisimo.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelisimo.api.MarvelRetrofit.marvelService
import com.example.marvelisimo.model.MarvelItem
import com.example.marvelisimo.model.MarvelItemDataWrapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class CharacterViewModel : ViewModel() {

    val characters: MutableLiveData<List<MarvelItem>> by lazy {
        MutableLiveData<List<MarvelItem>>().also {
            loadMarvelItems()
        }
    }

    @SuppressLint("CheckResult")
    fun loadMarvelItems(){

        val list = mutableListOf<MarvelItemDataWrapper>()

        marvelService.getAllCharacters(limit = 100)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result, err ->
                if (err?.message != null) Log.d(
                    "__",
                    "Error getAllCharacters " + err.message
                )
                else {
                    list.add(result)
                    
            }
    }


    fun generateCharacterList(heroList: List<MarvelItemDataWrapper>): List<MarvelItem> {

        val list = ArrayList<MarvelItem>()

        for (x in heroList[0].data.results.indices) {
            val item = heroList[0].data.results[x].description?.let {
                MarvelItem(
                    heroList[0].data.results[x].title,
                    heroList[0].data.results[x].imageUrl,
                    it,
                    //heroList[0].data.results[x].description
                )
            }
            list += item
        }
        return list
    }


