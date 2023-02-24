package com.example.coffeememos.search.recipe.domain.iterator

import com.example.coffeememos.search.recipe.domain.cache.RecipeMemoryCache
import com.example.coffeememos.search.recipe.domain.model.FilterRecipeInputData
import com.example.coffeememos.search.recipe.domain.serialization.RecipeSerializer
import com.example.coffeememos.search.recipe.domain.use_case.SetFilterRecipeInputDataUseCase
import javax.inject.Inject

class SetFilterRecipeInputDataIterator @Inject constructor()
    : SetFilterRecipeInputDataUseCase {
    @Inject
    lateinit var memoryCache: RecipeMemoryCache
    @Inject
    lateinit var serializer: RecipeSerializer

    override fun execute(inputData: FilterRecipeInputData) {
        val jsonStr = serializer.serialize(inputData)
        memoryCache.setData("filterRecipeInputData", jsonStr)
    }

}