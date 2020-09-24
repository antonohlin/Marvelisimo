package com.example.marvelisimo.viewModel

import android.telecom.Call
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelisimo.api.MarvelRetrofit
import com.example.marvelisimo.model.MarvelItem
import com.example.marvelisimo.model.MarvelItemDataWrapper
import com.example.marvelisimo.api.BASE_URL
import com.example.marvelisimo.api.MarvelService
import com.example.marvelisimo.api.getOkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MarvelItemViewModel : ViewModel() {

    val marvelItems: MutableLiveData<List<MarvelItem>> by lazy {

        val service: MarvelService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build()
                    .create(MarvelService::class.java)

        val marvelItems = MutableLiveData<List<MarvelItem>>()

        //NOTE: The "enque" call enqueues the request to a background thread.
        //Therefore we don't need to explicitly create a new threadpool or similar.
        service.getAllCharacters(limit=100).enqueue(object : retrofit2.Callback<List<MarvelItemDataWrapper>> {
            override fun onResponse(
                call: retrofit2.Call<List<MarvelItemDataWrapper>>,
                response: retrofit2.Response<List<MarvelItemDataWrapper>>
            ) {
                val marvelList = response.body()
                if (marvelList != null) {
                        marvelItems.postValue(marvelList)
                }
            }

            override fun onFailure(call: retrofit2.Call<List<MarvelItem>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    marvelItems
}



}







