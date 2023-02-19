package com.example.coffeememos.search.domain.iterator

import com.example.coffeememos.search.domain.model.FilterRecipeInputData
import com.example.coffeememos.search.domain.model.SearchRecipeModel
import com.example.coffeememos.search.domain.presenter.SearchRecipePresenter
import com.example.coffeememos.search.domain.repository.SearchRecipeDiskRepository
import com.example.coffeememos.search.domain.use_case.FilterRecipeUseCase
import javax.inject.Inject

class FilterRecipeIterator @Inject constructor() : FilterRecipeUseCase {
    @Inject
    lateinit var searchRecipeDiskRepository: SearchRecipeDiskRepository
    @Inject
    lateinit var searchRecipePresenter: SearchRecipePresenter

    override suspend fun filterRecipe(inputData: FilterRecipeInputData): List<SearchRecipeModel> {
        val recipes: List<SearchRecipeModel> =
            if (inputData.keyWord.isNotEmpty()) {
                 searchRecipeDiskRepository.searchRecipeByFreeWord(inputData.keyWord)
            } else {
                 searchRecipeDiskRepository.getAllRecipe()
            }

        if (recipes.isEmpty()) {
            return searchRecipePresenter.presentFilterResult(recipes)
        }

        // 酸味
        var result = getMatchedRecipes(recipes, inputData.sour) {
                recipe, value -> recipe.sour == value }
        if (result.isEmpty()) {
            return searchRecipePresenter.presentFilterResult(result)
        }
        // 苦味
        result = getMatchedRecipes(result, inputData.bitter) {
            recipe, data -> recipe.bitter == data
        }
        if (result.isEmpty()) {
            return searchRecipePresenter.presentFilterResult(result)
        }
        // 甘味
        result = getMatchedRecipes(result, inputData.sweet) {
                recipe, data -> recipe.sweet == data
        }
        if (result.isEmpty()) {
            return searchRecipePresenter.presentFilterResult(result)
        }
        // 香り
        result = getMatchedRecipes(result, inputData.flavor) {
                recipe, data -> recipe.flavor == data
        }
        if (result.isEmpty()) {
            return searchRecipePresenter.presentFilterResult(result)
        }
        // コク
        result = getMatchedRecipes(result, inputData.rich) {
                recipe, data -> recipe.rich == data
        }
        if (result.isEmpty()) {
            return searchRecipePresenter.presentFilterResult(result)
        }
        // 評価
        result = getMatchedRecipes(result, inputData.rating) {
                recipe, data -> recipe.rating == data
        }
        if (result.isEmpty()) {
            return searchRecipePresenter.presentFilterResult(result)
        }
        // 焙煎度
        result = getMatchedRecipes(result, inputData.roasts) {
                recipe, data -> recipe.roast == data
        }
        if (result.isEmpty()) {
            return searchRecipePresenter.presentFilterResult(result)
        }
        // 粒度
        result = getMatchedRecipes(result, inputData.grindSizes) {
                recipe, data -> recipe.grindSize == data
        }
        if (result.isEmpty()) {
            return searchRecipePresenter.presentFilterResult(result)
        }
        // 原産地
        result = getMatchedRecipes(result, inputData.countries) {
                recipe, data -> recipe.country.contains(data)
        }
        if (result.isEmpty()) {
            return searchRecipePresenter.presentFilterResult(result)
        }
        // 抽出器具
        result = getMatchedRecipes(result, inputData.tools) {
                recipe, data -> recipe.tool.contains(data)
        }

        return searchRecipePresenter.presentFilterResult(result)
    }

    private fun <T> getMatchedRecipes(
        recipes: List<SearchRecipeModel>,
        filteringData: List<T>,
        isMatch: (recipe: SearchRecipeModel, value: T) -> Boolean
    ): List<SearchRecipeModel> {
        if (filteringData.isEmpty()) return recipes

        val res: MutableList<SearchRecipeModel> = mutableListOf()
        for (recipe in recipes) {
            for (value in filteringData) {
                if (isMatch(recipe, value)) {
                    res.add(recipe)
                }
            }
        }

        return res
    }
}