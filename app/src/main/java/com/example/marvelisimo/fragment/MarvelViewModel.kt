package com.example.marvelisimo.fragment

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelisimo.api.MarvelRetrofit
import com.example.marvelisimo.model.ComicDataWrapper
import com.example.marvelisimo.model.MarvelItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MarvelViewModel : ViewModel() {

    @SuppressLint("CheckResult")
    fun callMarvelObjects(): MutableLiveData<List<ComicDataWrapper>> {
        val resultList = MutableLiveData<List<ComicDataWrapper>>()
        MarvelRetrofit.marvelService.getAllComics(limit = 100)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result, err ->
                if (err?.message != null) Log.d("__", "Error getAllCharacters " + err.message)
                else {
                    resultList.postValue(result)
                    Log.d("___", "I got a ComicDataWrapper $result")
                }
            }
        return resultList
    }

}