package com.example.marvelisimo.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.marvelisimo.R
import com.example.marvelisimo.db.DbHelper
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
        val favorite = findViewById<ImageView>(R.id.favorite)
        val db = DbHelper()
        val fp = FunctionProvider()
        findViewById<ImageView>(R.id.favorite_icon).visibility = View.INVISIBLE
        findViewById<ImageView>(R.id.search).visibility = View.INVISIBLE


        favorite.setOnClickListener {
            if (!fp.alreadyFavorited(marvelItem)) {
                db.saveToDb(marvelItem)
                Toast.makeText(this, "Favorite", Toast.LENGTH_SHORT).show()
                //TODO Change color of favorite icon

            } else {
                db.removeFromDb(marvelItem.id)
                Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show()
            }
        }

        Picasso.get().load(marvelItem.imageUrl).into(detail_image)
        detail_title.text = marvelItem.title
        description.text = marvelItem.description
        val descriptionUrl = marvelItem.url
        val i = Intent(Intent.ACTION_VIEW)

        findViewById<TextView>(R.id.moreInfoUrl).setOnClickListener() {
            i.data = Uri.parse(descriptionUrl)
            startActivity(i)
        }

        findViewById<TextView>(R.id.character_toolbar).setOnClickListener() {
            val intent = Intent(this, CharacterList::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.comics_toolbar).setOnClickListener() {
            val intent = Intent(this, ComicList::class.java)
            startActivity(intent)
        }
    }

}

