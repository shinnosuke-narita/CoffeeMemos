package com.withapp.coffeememo.search.recipe.domain.iterator

import com.withapp.coffeememo.search.recipe.domain.cache.RecipeMemoryCache
import com.withapp.coffeememo.search.recipe.domain.model.FilterRecipeInputData
import com.withapp.coffeememo.search.recipe.domain.serialization.RecipeSerializer
import com.withapp.coffeememo.search.recipe.domain.use_case.SetFilterRecipeInputDataUseCase
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