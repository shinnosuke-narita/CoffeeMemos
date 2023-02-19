package com.example.coffeememos.search.domain.serialization

import com.example.coffeememos.search.domain.model.FilterRecipeInputData

interface RecipeSerializer {
    fun serialize(filterRecipeInputData: FilterRecipeInputData): String
    fun deserialize(json: String): FilterRecipeInputData?
}