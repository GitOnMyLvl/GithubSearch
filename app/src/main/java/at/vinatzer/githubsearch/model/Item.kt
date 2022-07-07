package at.vinatzer.githubsearch.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Item(
    @JsonProperty("name") val name: String,
    @JsonProperty ("html_url") val html_url: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("owner") val owner : Owner
)


@JsonIgnoreProperties(ignoreUnknown = true)
data class Owner(
    @JsonProperty("login") val login: String
)