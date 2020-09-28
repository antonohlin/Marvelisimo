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

data class ComicDataWrapper (
    val code: Int,
    val status: String,
    val data: ComicDataContainer
)

data class ComicDataContainer (
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: Array<Comic>
)

data class Character(
    val id: Int,
    val thumbnail: Image,
    val name: String,
    val description: String,
    val urls: Array<MarvelUrl>
)

data class Comic (
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail: Image,
    val urls: Array<MarvelUrl>
)

data class Image(
    val path:String,
    val extension:String
)

data class MarvelUrl(
    val type:String,
    val url:String
)

