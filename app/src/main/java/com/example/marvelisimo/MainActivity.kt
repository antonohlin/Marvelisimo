package com.example.marvelisimo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val heroList = mutableListOf<CharacterDataWrapper>()




        //thread
        MarvelRetrofit.marvelService.getAllCharacters(limit = 100)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result, err ->
                if (err?.message != null) Log.d("__", "Error getAllCharacters " + err.message)
                else {
                    heroList.add(result)
                    Log.d("___", "I got a CharacterDataWrapper $result")
                    println(heroList.size)
                }
                println("PLZ FUNGERA: $heroList")
                println("PLZ FUNGERA: ${result.data.results[10].name}")
                val exampleList = generateDummyList(heroList.size, heroList)
                recycler_view.adapter = MarvelAdapter(exampleList)
                recycler_view.layoutManager = LinearLayoutManager(this)
                recycler_view.setHasFixedSize(true)
            }



    }
    private fun generateDummyList(size: Int, heroList: List<CharacterDataWrapper>): List<MarvelItem> {
        val list = ArrayList<MarvelItem>()
        for (i in 0 until size) {
            val drawable = when (i % 3) {
                0 -> R.drawable.ic_android
                1 -> R.drawable.ic_audio
                else -> R.drawable.ic_sun
            }
            for(x in heroList[0].data.results.indices) {
                val item = MarvelItem(
                    drawable,
                    heroList[0].data.results[x].name,
                    heroList[0].data.results[x].description
                )
                list += item
            }

        }
        return list
    }

}
