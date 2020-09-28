package com.example.marvelisimo.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.marvelisimo.R
import com.example.marvelisimo.api.MarvelRetrofit
import com.example.marvelisimo.api.MarvelRetrofit.md5
import com.example.marvelisimo.fragment.CharacterList.Companion.INTENT_PARCELABLE
import com.example.marvelisimo.model.MarvelItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_detail_view.*

class DetailView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_detail_view)
        setSupportActionBar(findViewById(R.id.toolbar))
        val marvelItem = intent.getParcelableExtra<MarvelItem>(INTENT_PARCELABLE)
        val url = changeUrl(marvelItem)
        val timestamp = System.currentTimeMillis().toString()
        val hash = (timestamp + MarvelRetrofit.PRIVATE_KEY + MarvelRetrofit.PUBLIC_KEY).md5()

        Picasso.get().load(url).into(detail_image)
        detail_title.text = marvelItem.title
        description.text = marvelItem.description
        Log.i("url", marvelItem.url)
        moreInfoUrl.text = marvelItem.url


    }

    fun changeUrl(item: MarvelItem): String{
        val url = item.imageUrl.replace("http", "https")+"."+item.extension
        return url
    }
}