package at.vinatzer.githubsearch

import android.util.Log
import androidx.lifecycle.MutableLiveData
import at.vinatzer.githubsearch.model.Item
import retrofit2.Callback
import retrofit2.Response
import at.vinatzer.githubsearch.model.Repositories
import at.vinatzer.githubsearch.web.EntryWebService
import retrofit2.Call


lateinit var response: at.vinatzer.githubsearch.Response
var repositories: ArrayList<Item> = arrayListOf<Item>()

class Response(
    private val entryWebService: EntryWebService
) {

    fun requestResponse(q: String) {
        entryWebService.getAllEntries(q).enqueue(object: Callback<Repositories> {
            override fun onResponse(
                call: Call<Repositories>,
                response: Response<Repositories>
            ) {
                val entries = response.body()
                if (entries == null) {
                    Log.e("Alert", "Response is null")
                }else{
                    repositories = entries.items as ArrayList
                    println(repositories)
                }
            }

            override fun onFailure(call: Call<Repositories>, t: Throwable) {
                Log.e("HTTP", "Get Repositories failed", t)
            }
        }
        )
    }

    fun getArrayList(): ArrayList<Item> {
        println(repositories)
        return repositories
    }


}