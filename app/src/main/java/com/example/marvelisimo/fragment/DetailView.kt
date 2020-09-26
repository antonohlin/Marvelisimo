package com.example.marvelisimo.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.marvelisimo.R
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

        Picasso.get().load(url).into(detail_image)
        detail_title.text = marvelItem.title
        //TODO: lägg till description

    }

    fun changeUrl(item: MarvelItem): String{
        val url = item.imageUrl.replace("http", "https")+"."+item.extension
        return url
    }
}