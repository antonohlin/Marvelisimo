package com.example.marvelisimo.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.marvelisimo.R
import com.example.marvelisimo.db.RealmStart
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        start_button.setOnClickListener { v -> goToNextView() }

    }

    init{
        RealmStart()
    }

    private fun fade() {
            val animation = AnimationUtils.loadAnimation(this, R.anim.fade_out)
            start_view.startAnimation(animation)
            Handler().postDelayed({
                marvelImage.visibility = View.GONE
            }, 2000)
        goToNextView()
    }

     private fun goToNextView() {
        val intent = Intent (this, CharacterList::class.java)
        startActivity(intent)
    }

    }





