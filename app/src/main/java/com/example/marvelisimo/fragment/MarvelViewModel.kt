package com.example.marvelisimo.fragment

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelisimo.api.MarvelRetrofit
import com.example.marvelisimo.model.CharacterDataWrapper
import com.example.marvelisimo.model.ComicDataWrapper
import com.example.marvelisimo.model.MarvelItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MarvelViewModel : ViewModel() {

    init {
        Log.i("Viewmodel","called")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("ViewModel", "destroyed!")
    }

    @SuppressLint("CheckResult")
    fun callComics(): LiveData<ComicDataWrapper> {
        val resultList = MutableLiveData<ComicDataWrapper>()
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

    @SuppressLint("CheckResult")
    fun callCharacters(): LiveData<CharacterDataWrapper> {
        val resultList = MutableLiveData<CharacterDataWrapper>()
        MarvelRetrofit.marvelService.getAllCharacters(limit = 100)
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