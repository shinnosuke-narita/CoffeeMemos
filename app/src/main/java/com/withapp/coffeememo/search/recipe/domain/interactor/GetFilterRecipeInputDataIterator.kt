package com.withapp.coffeememo.search.recipe.domain.interactor

import com.withapp.coffeememo.search.recipe.domain.cache.RecipeMemoryCache
import com.withapp.coffeememo.search.recipe.domain.model.FilterRecipeInputData
import com.withapp.coffeememo.search.recipe.domain.model.FilterRecipeOutputData
import com.withapp.coffeememo.search.recipe.domain.serialization.RecipeSerializer
import com.withapp.coffeememo.search.recipe.domain.use_case.GetFilterRecipeOutputDataUseCase
import javax.inject.Inject

class GetFilterRecipeInputDataIterator @Inject constructor(
    private val deserializer: RecipeSerializer,
    private val memoryCache: RecipeMemoryCache
) : GetFilterRecipeOutputDataUseCase {
    override  fun execute(key: String): FilterRecipeOutputData? {
        val jsonStr = memoryCache.getData(key)
        if (jsonStr.isEmpty()) {
            return null
        }

        val inputData: FilterRecipeInputData = deserializer.deserialize(jsonStr) ?: return null

        val sourValues: MutableList<Boolean> = MutableList(5) {false}
        val bitterValues: MutableList<Boolean> = MutableList(5) {false}
        val sweetValues: MutableList<Boolean> = MutableList(5) {false}
        val flavorValues: MutableList<Boolean> = MutableList(5) {false}
        val richValues: MutableList<Boolean> = MutableList(5) {false}
        val ratingValues: MutableList<Boolean> = MutableList(5) {false}
        val roastValues: MutableList<Boolean> = MutableList(8) {false}
        val grindSizeValues: MutableList<Boolean> = MutableList(5) {false}

        convertInputData(
            inputData = inputData.sour,
            outPutData = sourValues,
            shouldConvertIndex = true,
        )
        convertInputData(
            inputData = inputData.bitter,
            outPutData = bitterValues,
            shouldConvertIndex = true
        )
        convertInputData(
            inputData = inputData.sweet,
            outPutData = sweetValues,
            shouldConvertIndex = true
        )
        convertInputData(
            inputData = inputData.flavor,
            outPutData = flavorValues,
            shouldConvertIndex = true
        )
        convertInputData(
            inputData = inputData.rich,
            outPutData = richValues,
            shouldConvertIndex = true
        )
        convertInputData(
            inputData = inputData.rating,
            outPutData = ratingValues,
            shouldConvertIndex = true
        )
        convertInputData(
            inputData = inputData.grindSizes,
            outPutData = grindSizeValues,
            shouldConvertIndex = false
        )
        convertInputData(
            inputData = inputData.roasts,
            outPutData = roastValues,
            shouldConvertIndex = false
        )

        return FilterRecipeOutputData(
            sour = sourValues,
            bitter = bitterValues,
            sweet = sweetValues,
            flavor = flavorValues,
            rich = richValues,
            roasts = roastValues,
            grindSizes = grindSizeValues,
            rating = ratingValues,
            countries = inputData.countries,
            tools = inputData.tools
        )
    }

    private fun convertInputData(
        inputData: List<Int>,
        outPutData: MutableList<Boolean>,
        shouldConvertIndex: Boolean
    ) {
        if (inputData.isEmpty()) return

        for(inputValue in inputData) {
            var index: Int = inputValue
            if (shouldConvertIndex) {
                index--
            }

            outPutData[index] = true
        }
    }
}