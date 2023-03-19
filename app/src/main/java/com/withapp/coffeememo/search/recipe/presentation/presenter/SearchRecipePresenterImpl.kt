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
            inputData.sour,
            sourValues) { index, value ->
            index == value - 1
        }
        convertInputData(
            inputData.bitter,
            bitterValues) { index, value ->
            index == value - 1
        }
        convertInputData(
            inputData.sweet,
            sweetValues) { index, value ->
            index == value - 1
        }
        convertInputData(
            inputData.flavor,
            flavorValues) { index, value ->
            index == value - 1
        }
        convertInputData(
            inputData.rich,
            richValues) { index, value ->
            index == value - 1
        }
        convertInputData(
            inputData.rating,
            ratingValues) { index, value ->
            index == value - 1
        }
        convertInputData(
            inputData.grindSizes,
            grindSizeValues) { index, value ->
            index == value
        }
        convertInputData(
            inputData.roasts,
            roastValues) { index, value ->
            index == value
        }

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
        isMatch: (index: Int, value: Int) -> Boolean) {
        if (inputData.isEmpty()) return

        for ((i, _) in outPutData.withIndex()) {
            for (inputValue in inputData) {
                if (isMatch(i, inputValue)) {
                    outPutData[i] = true
                }
            }
        }

    }
}