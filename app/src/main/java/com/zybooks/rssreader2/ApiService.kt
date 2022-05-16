package com.zybooks.rssreader2

import com.zybooks.rssreader2.model.Rss

import retrofit2.Call

import retrofit2.http.GET


interface ApiService {
    @get:GET("rss2.0.xml")
    val rss: Call<Rss>

    companion object {
        const val BASE_URL = "https://www.sandiegouniontribune.com/news/"
    }
}
