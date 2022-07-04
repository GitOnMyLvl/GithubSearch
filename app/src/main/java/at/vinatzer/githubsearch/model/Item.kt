package at.vinatzer.githubsearch.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Item(
    @JsonProperty("description") val description: Any?,
    @JsonProperty("html_url") val html_url: String,
    @JsonProperty("name") val name: String
)