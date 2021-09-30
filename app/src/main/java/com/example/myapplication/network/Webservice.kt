package com.example.myapplication.network

import com.example.myapplication.ui.homePage.model.Data
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface Webservice {

    companion object{
        val URL = "https://app05test.ulifestyle.com.hk/"

        val webservice by lazy {
            Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build().create(Webservice::class.java)
        }
    }

    @GET("ulifestyle-app/miniProgramList?apiVersionKey=ulapp-android-4.9.0&clientKey=h5BQ82nQ")
    suspend fun getData(): List<Data>

}