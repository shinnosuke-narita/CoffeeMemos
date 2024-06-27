package com.withapp.coffeememo.domain.usecase.recipe.savefilterelement

import com.withapp.coffeememo.domain.repository.RecipeCacheRepository
import com.withapp.coffeememo.domain.model.recipe.FilterRecipeInputData
import com.withapp.coffeememo.domain.serialization.recipe.RecipeSerializer
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