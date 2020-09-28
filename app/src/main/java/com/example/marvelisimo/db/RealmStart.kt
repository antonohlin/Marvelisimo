package com.example.marvelisimo.db

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmStart : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        val configuration =
            RealmConfiguration.Builder().schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .name("marvelDB").build()
        Realm.setDefaultConfiguration(configuration)
    }

}

