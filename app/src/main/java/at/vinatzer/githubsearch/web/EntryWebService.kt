package at.vinatzer.githubsearch.web

import retrofit2.Call
import retrofit2.http.GET
import at.vinatzer.githubsearch.model.Repositories
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

interface EntryWebService{
    @GET("repositories?q=tetris+language:assembly&sort=stars&order=desc")
    fun getAllEntries(): Call<Repositories>
}

fun createWebService(): EntryWebService {
    val httpClient = OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/search/")
        .addConverterFactory(JacksonConverterFactory.create())
        .client(httpClient)
        .build()

    return retrofit.create(EntryWebService::class.java)
}