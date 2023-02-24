package com.example.coffeememos.search.recipe.domain.model

data class FilterRecipeOutputData(
    val countries: List<String>,
    val tools: List<String>,
    val roasts: List<Boolean>,
    val grindSizes: List<Boolean>,
    val rating: List<Boolean>,
    val sour: List<Boolean>,
    val bitter: List<Boolean>,
    val sweet: List<Boolean>,
    val flavor: List<Boolean>,
    val rich: List<Boolean>
)



