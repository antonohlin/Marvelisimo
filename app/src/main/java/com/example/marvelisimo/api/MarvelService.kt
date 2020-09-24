package com.example.marvelisimo.api

import com.example.marvelisimo.model.MarvelItemDataWrapper
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

private const val LOG = false
private const val PUBLIC_KEY = "b8bd59c182e2fddefff77aa8c8c64c20"
private const val PRIVATE_KEY = "8a6dfc3b363b0dfb2c532144c20268cb96004fc0"
const val BASE_URL = "https://gateway.marvel.com/v1/public/"

interface MarvelService {

    @GET("characters")
    fun getAllCharacters(
        @Path("nameStartsWith") nameStartsWith: String? = null,
        @Path("name") byExactName: String? = null,
        @Path("limit") limit: Int? = null,
    ): Call<List<MarvelItemDataWrapper>>


    @GET("comics")
    fun getAllComics(
        @Query("titleStartsWith") titleStartsWith: String? = null,
        @Query("title") byExactTitle: String? = null,
        @Query("characters") orderBy: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): Call<List<MarvelItemDataWrapper>>
}

    fun String.md5(): String {
        val MD5 = "MD5"
        try { // Create MD5 Hash
            val digest = MessageDigest
                .getInstance(MD5)
            digest.update(this.toByteArray())
            val messageDigest = digest.digest()
            // Create Hex String
            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2) h = "0$h"
                hexString.append(h)
            }
            return hexString.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }

     fun getOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = if (LOG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val builder = OkHttpClient.Builder()
            .addInterceptor{ chain ->
                val original = chain.request()
                val originalHttpUrl = original.url()
                val timestamp = System.currentTimeMillis().toString()
                val hash = (timestamp + PRIVATE_KEY + PUBLIC_KEY).md5()

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("apikey", PUBLIC_KEY)
                    .addQueryParameter("ts", timestamp)
                    .addQueryParameter("hash", hash)
                    .build()

                val requestBuilder = original.newBuilder()
                    .url(url)

                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .addInterceptor(logging)
        return builder.build()
    }
