package com.example.coffeememos.search.domain.iterator

import com.example.coffeememos.search.domain.cache.RecipeMemoryCache
import com.example.coffeememos.search.domain.model.FilterRecipeInputData
import com.example.coffeememos.search.domain.model.FilterRecipeOutputData
import com.example.coffeememos.search.domain.presenter.SearchRecipePresenter
import com.example.coffeememos.search.domain.serialization.RecipeSerializer
import com.example.coffeememos.search.domain.use_case.GetFilterRecipeOutputDataUseCase
import javax.inject.Inject

class GetFilterRecipeInputDataIterator @Inject constructor()
    : GetFilterRecipeOutputDataUseCase {
    @Inject
    lateinit var deserializer: RecipeSerializer
    @Inject
    lateinit var memoryCache: RecipeMemoryCache
    @Inject
    lateinit var presenter: SearchRecipePresenter

    override  fun execute(key: String): FilterRecipeOutputData? {
        val jsonStr = memoryCache.getData(key)
        if (jsonStr.isEmpty()) {
            return null
        }

        val inputData: FilterRecipeInputData =
            deserializer.deserialize(jsonStr)
                ?: return null

        return presenter.presentFilterOutputData(inputData)
    }
}