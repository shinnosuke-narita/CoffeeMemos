package com.example.coffeememos.search.presentation.presenter

import com.example.coffeememos.search.domain.model.FilterRecipeInputData
import com.example.coffeememos.search.domain.model.FilterRecipeOutputData
import com.example.coffeememos.search.domain.presenter.SearchRecipePresenter
import com.example.coffeememos.search.domain.model.SearchRecipeModel
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
        if (inputData.isNotEmpty()) {
            for ((i, value) in inputData.withIndex()) {
                if (isMatch(i, value)) {
                    outPutData[i] = true
                }
            }
        }
    }
}