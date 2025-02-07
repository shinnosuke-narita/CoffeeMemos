package com.withapp.coffeememo.presentation.home.recipe.model

import java.time.LocalDateTime

data class HomeRecipeCardData(
    val recipeId  : Long,
    val beanId    : Long,
    val country   : String,
    val createdAt : LocalDateTime,
    val tool      : String,
    val roast     : String,
    val rating    : String,
    var isFavorite: Boolean
)