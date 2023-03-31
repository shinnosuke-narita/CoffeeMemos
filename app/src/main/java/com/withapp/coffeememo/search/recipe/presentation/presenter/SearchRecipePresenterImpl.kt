package com.withapp.coffeememo.search.recipe.presentation.presenter

import com.withapp.coffeememo.search.recipe.domain.model.FilterRecipeInputData
import com.withapp.coffeememo.search.recipe.domain.model.FilterRecipeOutputData
import com.withapp.coffeememo.search.recipe.domain.model.SearchRecipeModel
import com.withapp.coffeememo.search.recipe.domain.presenter.SearchRecipePresenter
import javax.inject.Inject

class SearchRecipePresenterImpl @Inject constructor() : SearchRecipePresenter {
    override fun presentAllRecipes(recipes: List<SearchRecipeModel>): List<SearchRecipeModel> {
        return recipes
    }

    override fun presentFreeWordSearchRes(
        recipes: List<SearchRecipeModel>
    ): List<SearchRecipeModel> {
        if (recipes.isEmpty()) return listOf()

        return recipes
    }

    override fun presentFilterResult(
        recipes: List<SearchRecipeModel>
    ): List<SearchRecipeModel> {
        return recipes
    }

    override fun presentFilterOutputData(
        inputData: FilterRecipeInputData
    ): FilterRecipeOutputData {
        val sourValues: MutableList<Boolean> = MutableList(5) {false}
        val bitterValues: MutableList<Boolean> = MutableList(5) {false}
        val sweetValues: MutableList<Boolean> = MutableList(5) {false}
        val flavorValues: MutableList<Boolean> = MutableList(5) {false}
        val richValues: MutableList<Boolean> = MutableList(5) {false}
        val ratingValues: MutableList<Boolean> = MutableList(5) {false}
        val roastValues: MutableList<Boolean> = MutableList(5) {false}
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