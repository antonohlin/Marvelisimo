package com.example.marvelisimo.model

data class MarvelResponse(val status: Int?, val msg: String?, val data: List<MarvelItem>?) {
    fun isSuccess(): Boolean = (status == 200)
}