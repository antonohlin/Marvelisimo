package com.example.marvelisimo.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.marvelisimo.R
import com.example.marvelisimo.fragment.CharacterList.Companion.INTENT_PARCELABLE
import com.example.marvelisimo.model.MarvelItem

class DetailView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_detail_view)

        val marvelItem = intent.getParcelableExtra<MarvelItem>(CharacterList.INTENT_PARCELABLE)

    }
}