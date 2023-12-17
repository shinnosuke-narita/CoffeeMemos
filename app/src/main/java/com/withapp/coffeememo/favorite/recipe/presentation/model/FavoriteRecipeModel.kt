package com.withapp.coffeememo.favorite.recipe.presentation.model

data class FavoriteRecipeModel(
    val id: Long,
    var beanId: Long,
    var country: String,
    val tool: String,
    val roast: String,
    val extractionTime: Long,
    val preInfusionTime: Long,
    val amountExtraction: String,
    val temperature: String,
    val grindSize: String,
    val amountOfBeans: String,
    val comment: String,
    val isFavorite: Boolean,
    val rating: Int,
    val createdAt: Long
)
