package com.example.marvelisimo.fragment

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
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
        description.text = marvelItem.description
        Log.i("url", marvelItem.url)
        val descriptionUrl = marvelItem.url
        val i = Intent(Intent.ACTION_VIEW)
        findViewById<TextView>(R.id.moreInfoUrl).setOnClickListener(){
            i.data = Uri.parse(descriptionUrl)
            startActivity(i)
        }


    }

    fun changeUrl(item: MarvelItem): String{
        return item.imageUrl+"."+item.extension
    }
}