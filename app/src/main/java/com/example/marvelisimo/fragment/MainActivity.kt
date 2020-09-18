package com.example.marvelisimo.fragment

import android.content.Intent
import android.icu.util.TimeUnit
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.marvelisimo.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        start_button.setOnClickListener { v -> fade() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.layout_navbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var itemview = item.itemId
        when (itemview) {
            R.id.search_bar -> Toast.makeText(this, "Search clicked!", Toast.LENGTH_SHORT)
        }
        return false
    }

    private fun fade() {
            val animation = AnimationUtils.loadAnimation(this, R.anim.fade_out)
            start_view.startAnimation(animation)
            Handler().postDelayed({
                marvelImage.visibility = View.GONE
            }, 2000)
        goToNextView()
    }

     fun goToNextView() {
        val intent = Intent (this, CharacterList::class.java)
        startActivity(intent)
    }

    }





