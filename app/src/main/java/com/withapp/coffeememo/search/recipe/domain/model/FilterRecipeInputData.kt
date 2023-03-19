package com.withapp.coffeememo.search.recipe.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilterRecipeInputData(
    @SerialName("key_word")
    val keyWord: String,
    @SerialName("countries")
    val countries: List<String>,
    @SerialName("tools")
    val tools: List<String>,
    @SerialName("roasts")
    val roasts: List<Int>,
    @SerialName("grind_size")
    val grindSizes: List<Int>,
    @SerialName("rating")
    val rating: List<Int>,
    @SerialName("sour")
    val sour: List<Int>,
    @SerialName("bitter")
    val bitter: List<Int>,
    @SerialName("sweet")
    val sweet: List<Int>,
    @SerialName("flavor")
    val flavor: List<Int>,
    @SerialName("rich")
    val rich: List<Int>
)



