package com.example.marvelisimo.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.marvelisimo.api.MarvelRetrofit
import com.example.marvelisimo.model.MarvelItem
import kotlinx.coroutines.*
import retrofit2.HttpException

class MarvelRepo(){

    private var marvelItems = mutableListOf<MarvelItem>()
    private var mutableLiveData = MutableLiveData<List<MarvelItem>>()
    val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)


    fun getMutableLiveData(): MutableLiveData<List<MarvelItem>> {
        coroutineScope.launch {
            val request = MarvelRetrofit.marvelService.getAllCharacters()
            withContext(Dispatchers.Main) {
                try {
                     mutableLiveData.value = marvelItems;

                } catch (e: HttpException) {
                    // Log exception //

                } catch (e: Throwable) {
                    // Log error //)
                }
            }
        }
        return mutableLiveData;
    }
}