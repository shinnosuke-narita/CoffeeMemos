package com.withapp.coffeememo.search.bean.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilterBeanInputData(
    @SerialName("key_word")
    val keyWord: String,
    @SerialName("countries")
    val countries: List<String>,
    @SerialName("farms")
    val farms: List<String>,
    @SerialName("districts")
    val districts: List<String>,
    @SerialName("stores")
    val stores: List<String>,
    @SerialName("species")
    val species: List<String>,
    @SerialName("rating")
    val rating: List<Int>,
    @SerialName("process")
    val process: List<Int>
)



