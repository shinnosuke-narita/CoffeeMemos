package com.withapp.coffeememo.domain.serialization.recipe

import com.withapp.coffeememo.domain.model.recipe.FilterRecipeInputData

interface RecipeSerializer {
    fun serialize(filterRecipeInputData: FilterRecipeInputData): String
    fun deserialize(json: String): FilterRecipeInputData?
}