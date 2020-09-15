package com.example.marvelisimo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val exampleList = generateDummyList(100)

        recycler_view.adapter = MarvelAdapter(exampleList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        //thread
        MarvelRetrofit.marvelService.getAllCharacters(limit = 1, offset = 1)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result, err ->
                if (err?.message != null) Log.d("__", "Error getAllCharacters " + err.message)
                else {
                    Log.d("___", "I got a CharacterDataWrapper $result")
                }
            }

    }
    private fun generateDummyList(size: Int): List<MarvelItem> {
        val list = ArrayList<MarvelItem>()
        for (i in 0 until size) {
            val drawable = when (i % 3) {
                0 -> R.drawable.ic_android
                1 -> R.drawable.ic_audio
                else -> R.drawable.ic_sun
            }
            val item = MarvelItem(drawable, "Item $i", "Line 2")
            list += item
        }
        return list
    }

}
