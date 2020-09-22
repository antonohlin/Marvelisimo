import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmObject

class Realm : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        val configuration =
            RealmConfiguration.Builder().schemaVersion(1).deleteRealmIfMigrationNeeded()
                .name("marvelDB").build()
        Realm.setDefaultConfiguration(configuration)
    }

}

// Queries
//val results = realm.where<EnumTest>().equalTo("strField", MyEnum.Value1.name).findAll()