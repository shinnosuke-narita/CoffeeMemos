package com.withapp.coffeememo.domain.model.bean

data class FilterBeanOutputData(
    val countries: List<String>,
    val farms: List<String>,
    val districts: List<String>,
    val stores: List<String>,
    val species: List<String>,
    val rating: List<Boolean>,
    val process: List<Boolean>
)



