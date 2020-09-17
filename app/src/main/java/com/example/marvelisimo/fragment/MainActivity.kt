package com.example.marvelisimo.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.marvelisimo.R
import com.example.marvelisimo.R.id
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      val marvelLogo: ImageView = findViewById(id.marvelImage)
      marvelLogo.setOnClickListener() {goToNextView()}
    }

    private fun fade(img: ImageView) {
        img.animate().alpha(0f).duration = 2000
    }

     fun goToNextView() {
        val intent = Intent (this, CharacterList::class.java)
        startActivity(intent)
    }

}





