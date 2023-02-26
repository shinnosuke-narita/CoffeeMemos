package com.example.coffeememos.home.recipe.domain.model

import java.time.LocalDateTime

data class HomeRecipeModel(
    val recipeId  : Long,
    val beanId    : Long,
    val country   : String,
    val createdAt : LocalDateTime,
    val tool      : String,
    val roast     : Int,
    val rating    : Int,
    val isFavorite: Boolean
)
