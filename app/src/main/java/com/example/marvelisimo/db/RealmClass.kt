package com.example.marvelisimo.db

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class RealmMarvelItem (
    @PrimaryKey var id: Int = 0,
    var name: String = "",
    var description: String = "",
    var imageUrlBase: String = "",
    var extension: String = "",
    var url: String = ""
): RealmObject() {

//open class Image(
//    // Properties can be annotated with PrimaryKey or Index.
//    @PrimaryKey var id: Long = 0,
//    var path: String,
//    var extension: String,
//): RealmObject()

//val dog = Dog()
//dog.name = "Rex"
//dog.age = 1

// Initialize Realm (just once per application)
//Realm.init(context)

// Get a Realm instance for this thread
//realm = Realm.getDefaultInstance()

// Query Realm for all dogs younger than 2 years old
//val puppies = realm.where<Dog>().lessThan("age", 2).findAll()
//puppies.size // => 0 because no dogs have been added to the Realm yet

// Persist your data in a transaction
//realm.beginTransaction()
//val managedDog = realm.copyToRealm(dog) // Persist unmanaged objects
//val person = realm.createObject<Person>(0) // Create managed objects directly
//person.dogs.add(managedDog)
//realm.commitTransaction()

// Listeners will be notified when data changes
//puppies.addChangeListener { results, changeSet ->
    // Query results are updated in real time with fine grained notifications.
    //changeSet.insertions // => [0] is added.
//}

// Asynchronously update objects on a background thread
//realm.executeTransactionAsync(Realm.Transaction { bgRealm ->
    // Find a dog to update.
    //val dog = bgRealm.where<Dog>().equalTo("age", 1.toInt()).findFirst()!!
    //dog.age = 3 // Update its age value.
//}, Realm.Transaction.OnSuccess {
    // Original queries and Realm objects are automatically updated.
  //  puppies.size // => 0 because there are no more puppies younger than 2 years old
    //managedDog.age // => 3 the dogs age is updated
//})