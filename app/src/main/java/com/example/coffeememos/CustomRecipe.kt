package com.example.coffeememos

data class CustomRecipe (
    val recipeId  : Long,
    val beanId    : Long,
    val country   : String,
    val tool      : String,
    val roast     : Int,
    val grindSize : Int,
    val createdAt : Long,
    val sour      : Int,
    val bitter    : Int,
    val sweet     : Int,
    val flavor    : Int,
    val rich      : Int,
    val rating    : Int,
    val isFavorite: Boolean)