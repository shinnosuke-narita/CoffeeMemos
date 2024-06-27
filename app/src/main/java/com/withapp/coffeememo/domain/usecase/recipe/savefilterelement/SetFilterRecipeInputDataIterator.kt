package com.withapp.coffeememo.domain.usecase.recipe.savefilterelement

import com.withapp.coffeememo.domain.repository.RecipeCacheRepository
import com.withapp.coffeememo.search.recipe.domain.model.FilterRecipeInputData
import com.withapp.coffeememo.domain.serialization.RecipeSerializer
import javax.inject.Inject

class SetFilterRecipeInputDataIterator @Inject constructor()
    : SetFilterRecipeInputDataUseCase {
    @Inject
    lateinit var memoryCache: RecipeCacheRepository
    @Inject
    lateinit var serializer: RecipeSerializer

    override fun execute(inputData: FilterRecipeInputData) {
        val jsonStr = serializer.serialize(inputData)
        memoryCache.setData("filterRecipeInputData", jsonStr)
    }

}