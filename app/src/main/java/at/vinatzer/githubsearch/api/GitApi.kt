import at.vinatzer.githubsearch.model.Repositories
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GitApi {

    @GET("search/repositories")
    fun searchRepositories(
        @Query("q") searchQuery: String,
        @Query("page") pageIndex: Int,
        @Query("per_page") perPage: Int
    ): Call<Repositories>
}