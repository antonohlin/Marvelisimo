package com.example.marvelisimo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.annotation.IdRes

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun fade(view: View) {
        val marvelLogo: ImageView = findViewById(R.id.marvelLogo)
        marvelLogo.animate().alpha(0f).duration = 2000

    }


}
