package com.example.coffeememos.search.recipe.domain.serialization

import com.example.coffeememos.search.recipe.domain.model.FilterRecipeInputData

interface RecipeSerializer {
    fun serialize(filterRecipeInputData: FilterRecipeInputData): String
    fun deserialize(json: String): FilterRecipeInputData?
}