package com.example.marvelisimo.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.VISIBLE
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelisimo.R
import com.example.marvelisimo.adapter.MarvelAdapter
import com.example.marvelisimo.api.MarvelRetrofit
import com.example.marvelisimo.model.CharacterDataWrapper
import com.example.marvelisimo.model.ComicDataWrapper
import com.example.marvelisimo.model.MarvelItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_character_list_.*

class CharacterList : AppCompatActivity() {

    companion object {
        const val INTENT_PARCELABLE = "MARVEL_ITEM"
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_character_list_)
        setSupportActionBar(findViewById(R.id.toolbar))
        val comicsToolbarLink = findViewById<TextView>(R.id.comics_toolbar)
        comicsToolbarLink.setOnClickListener {
            goToComics()
        }

        val heroList = mutableListOf<CharacterDataWrapper>()



        if (isOnline(this)) {
            MarvelRetrofit.marvelService.getAllCharacters(limit = 100)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result, err ->
                    if (err?.message != null) Log.d("__", "Error getAllCharacters " + err.message)
                    else {
                        heroList.add(result)
                        Log.d("___", "I got a CharacterDataWrapper $result")
                        println(heroList.size)
                    }

                    val characterList = generateCharacterList(heroList)

                    recycler_view.layoutManager = LinearLayoutManager(this)
                    recycler_view.setHasFixedSize(true)
                    recycler_view.adapter = MarvelAdapter(this, characterList) {
                        val intent = Intent(this, DetailView::class.java)
                        intent.putExtra(INTENT_PARCELABLE, it)
                        startActivity(intent)
                    }
                }
        } else {
            println("No internet lol")
            findViewById<TextView>(R.id.no_connection).visibility = VISIBLE
        }
    }
    private fun generateCharacterList(heroList: List<CharacterDataWrapper>): List<MarvelItem> {

        val list = ArrayList<MarvelItem>()

        for (x in heroList[0].data.results.indices) {
            val item = MarvelItem(
                heroList[0].data.results[x].name,
                heroList[0].data.results[x].thumbnail.path,
                heroList[0].data.results[x].thumbnail.extension,
                //heroList[0].data.results[x].description
            )
            list += item
        }
        return list
    }
    private fun goToComics() {
        val intent = Intent (this, ComicList::class.java)
        startActivity(intent)
    }
    private fun isOnline(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
            return false
    }
}

