package at.vinatzer.githubsearch.web

import retrofit2.Call
import retrofit2.http.GET
import at.vinatzer.githubsearch.model.Repositories
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface EntryWebService{
    @GET("repositories?")
    fun getAllEntries(
        @Query(value = "q") repositoryName: String
    ): Call<Repositories>
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