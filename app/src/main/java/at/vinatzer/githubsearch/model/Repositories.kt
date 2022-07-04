package at.vinatzer.githubsearch.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Repositories(
    @JsonProperty("items") val items: List<Item>,
)