package at.vinatzer.githubsearch.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class DetailViewModel (private val savedStateHandle: SavedStateHandle): ViewModel() {
    val getRepoName = savedStateHandle.get<String>("name")
    val getRepoOwner = savedStateHandle.get<String>("owner")
    val getRepoDescription = savedStateHandle.get<String>("description")
    val getRepoUrl = savedStateHandle.get<String>("html_url")
}