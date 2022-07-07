package at.vinatzer.githubsearch

import androidx.lifecycle.ViewModel
import at.vinatzer.githubsearch.model.Item

class MainViewModel : ViewModel() {
    var pageNumber: Int = 1
    var query: String = ""
    var entriesPerPage: Int = 20
    var repoList: MutableList<Item> = mutableListOf()
    fun getResponse() = response.getResponse(query, pageNumber, entriesPerPage)
}