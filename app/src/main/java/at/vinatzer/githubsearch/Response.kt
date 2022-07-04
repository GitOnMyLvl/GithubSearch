package at.vinatzer.githubsearch

import android.util.Log
import retrofit2.Callback
import retrofit2.Response
import at.vinatzer.githubsearch.model.Repositories
import at.vinatzer.githubsearch.web.EntryWebService
import retrofit2.Call

lateinit var response: at.vinatzer.githubsearch.Response

class Response(
    private val entryWebService: EntryWebService
) {
    fun requestResponse() {
        entryWebService.getAllEntries().enqueue(object: Callback<Repositories> {
            override fun onResponse(
                call: Call<Repositories>,
                response: Response<Repositories>
            ) {
                val entries = response.body()
                println(entries)
            }

            override fun onFailure(call: Call<Repositories>, t: Throwable) {
                Log.e("HTTP", "Get entries failed", t)
            }
        }
        )
    }

}