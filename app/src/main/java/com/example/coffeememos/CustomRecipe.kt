package com.example.coffeememos

data class CustomRecipe (
    val recipeId        : Long,
    val beanId          : Long,
    val tasteId         : Long,
    val country         : String,
    val tool            : String,
    val roast           : Int,
    val grindSize       : Int,
    val createdAt       : Long,
    val amountBean      : Int,
    val amountExtraction: Int,
    val temperature     : Int,
    val preInfusionTime : Long,
    val extractionTime  : Long,
    val rating          : Int,
    val isFavorite      : Boolean)