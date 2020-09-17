package com.example.marvelisimo.model


data class CharacterDataWrapper(
    val code: Int,
    val status: String,
    val data: CharacterDataContainer
)

data class CharacterDataContainer(
    val total: Int,
    val count: Int,
    val results: Array<Character>
)
data class Character(
    val id: Int, //, optional): The unique ID of the character resource.,
    val thumbnail: Image,
    val name: String, //, optional): The name of the character.,
    val description: String //, optional): A short bio or description of the character.,
)

data class Image(
    val path:String,
    val extension:String
)
