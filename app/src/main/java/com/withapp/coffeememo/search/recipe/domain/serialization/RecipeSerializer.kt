package com.withapp.coffeememo.search.recipe.domain.serialization

import com.withapp.coffeememo.search.recipe.domain.model.FilterRecipeInputData

interface RecipeSerializer {
    fun serialize(filterRecipeInputData: FilterRecipeInputData): String
    fun deserialize(json: String): FilterRecipeInputData?
}