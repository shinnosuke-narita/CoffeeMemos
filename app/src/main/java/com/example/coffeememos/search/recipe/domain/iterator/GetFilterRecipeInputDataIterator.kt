package com.example.coffeememos.search.recipe.domain.iterator

import com.example.coffeememos.search.recipe.domain.cache.RecipeMemoryCache
import com.example.coffeememos.search.recipe.domain.model.FilterRecipeInputData
import com.example.coffeememos.search.recipe.domain.model.FilterRecipeOutputData
import com.example.coffeememos.search.recipe.domain.presenter.SearchRecipePresenter
import com.example.coffeememos.search.recipe.domain.serialization.RecipeSerializer
import com.example.coffeememos.search.recipe.domain.use_case.GetFilterRecipeOutputDataUseCase
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