package com.withapp.coffeememo.search.recipe.domain.iterator

import com.withapp.coffeememo.search.recipe.domain.cache.RecipeMemoryCache
import com.withapp.coffeememo.search.recipe.domain.model.FilterRecipeInputData
import com.withapp.coffeememo.search.recipe.domain.model.FilterRecipeOutputData
import com.withapp.coffeememo.search.recipe.domain.presenter.SearchRecipePresenter
import com.withapp.coffeememo.search.recipe.domain.serialization.RecipeSerializer
import com.withapp.coffeememo.search.recipe.domain.use_case.GetFilterRecipeOutputDataUseCase
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