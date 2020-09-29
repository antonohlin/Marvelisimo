package com.example.marvelisimo.fragment

import android.R.attr.name
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.marvelisimo.db.RealmMarvelItem
import com.example.marvelisimo.model.MarvelItem
import io.realm.Realm
import io.realm.kotlin.where


class FunctionProvider {
    val realm: Realm = Realm.getDefaultInstance()
    fun isOnline(context: Context): Boolean {
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

    fun alreadyFavorited(marvelItem: MarvelItem): Boolean{
        var favorite = false
        val favoritesResult = realm.where<RealmMarvelItem>().findAll()
        for (x in favoritesResult){
         if (marvelItem.title == x.name){
            favorite = true
            }
        }
        return favorite
    }
}