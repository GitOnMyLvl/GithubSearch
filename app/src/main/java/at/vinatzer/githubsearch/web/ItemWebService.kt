package at.vinatzer.githubsearch.web

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ItemWebService : Application() {
    companion object {
        const val URL = "https://api.github.com"
        lateinit var retrofit: Retrofit
    }

    override fun onCreate() {
        super.onCreate()
        retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}