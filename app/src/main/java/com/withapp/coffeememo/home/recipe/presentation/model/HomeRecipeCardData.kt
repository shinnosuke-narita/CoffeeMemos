package com.withapp.coffeememo.home.recipe.presentation.model

data class HomeRecipeCardData(
    val recipeId  : Long,
    val beanId    : Long,
    val country   : String,
    val createdAt : String,
    val tool      : String,
    val roast     : String,
    val rating    : String,
    var isFavorite: Boolean
)