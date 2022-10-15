package com.example.coffeememos

data class SimpleRecipe(
    val recipeId: Long,
    val country: String,
    val createdAt: String,
    val tool: String,
    val roast: String,
    val rating: String,
    val isFavorite: Boolean
)