package at.vinatzer.githubsearch

import androidx.lifecycle.ViewModel
import at.vinatzer.githubsearch.model.Item

class MainViewModel: ViewModel() {

    var search: String = ""
    fun requestResponse() = response.requestResponse(search)
    fun getArrayList() = response.getArrayList()
}