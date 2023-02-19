package com.example.coffeememos.search.domain.iterator

import com.example.coffeememos.search.domain.cache.RecipeMemoryCache
import com.example.coffeememos.search.domain.model.FilterRecipeInputData
import com.example.coffeememos.search.domain.serialization.RecipeSerializer
import com.example.coffeememos.search.domain.use_case.SetFilterRecipeInputDataUseCase
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