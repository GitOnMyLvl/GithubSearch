package at.vinatzer.githubsearch.model

data class Repositories(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)