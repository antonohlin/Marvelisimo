package com.example.marvelisimo.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.marvelisimo.R

class FavoriteList : AppCompatActivity() {

    companion object {
        const val INTENT_PARCELABLE = "MARVEL_ITEM"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_character_list_)
        setSupportActionBar(findViewById(R.id.toolbar))
    }
}