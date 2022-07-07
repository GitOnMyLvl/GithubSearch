package at.vinatzer.githubsearch

import GitApi
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Callback
import retrofit2.Response
import at.vinatzer.githubsearch.model.Repositories
import at.vinatzer.githubsearch.web.ItemWebService
import retrofit2.Call

lateinit var response: at.vinatzer.githubsearch.Response

class Response{

    fun getResponse(query: String, page: Int, perPage: Int) : MutableLiveData<Repositories>{
        val responseLiveData: MutableLiveData<Repositories> = MutableLiveData()

        val repository = ItemWebService.retrofit.create(GitApi::class.java)
        repository.searchRepositories(query, page, perPage).enqueue(object : Callback<Repositories>{
            override fun onResponse(call: Call<Repositories>, response: Response<Repositories>) {
                if (response.code() == 200) {
                    Log.d(ContentValues.TAG, "Response: ${response.body()}")
                    responseLiveData.value = response.body() as Repositories
                } else {
                    Log.d(ContentValues.TAG, "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Repositories>, t: Throwable) {
                Log.e(ContentValues.TAG, "Failed to load")
            }
        })
        return responseLiveData
    }
}
