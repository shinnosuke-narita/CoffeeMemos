package com.withapp.coffeememo.search.recipe.domain.iterator

import com.withapp.coffeememo.domain.repository.RecipeRepository
import com.withapp.coffeememo.search.recipe.data.mapper.SearchRecipeModelMapper
import com.withapp.coffeememo.search.recipe.domain.model.FilterRecipeInputData
import com.withapp.coffeememo.search.recipe.domain.model.SearchRecipeModel
import com.withapp.coffeememo.search.recipe.domain.presenter.SearchRecipePresenter
import com.withapp.coffeememo.search.recipe.domain.use_case.FilterRecipeUseCase
import javax.inject.Inject

class FilterRecipeIterator @Inject constructor(
    private val recipeRepo: RecipeRepository,
    private val mapper: SearchRecipeModelMapper,
    private val searchRecipePresenter: SearchRecipePresenter
) : FilterRecipeUseCase {
    override suspend fun filterRecipe(inputData: FilterRecipeInputData): List<SearchRecipeModel> {
        val recipes: List<SearchRecipeModel> =
            if (inputData.keyWord.isNotEmpty()) {
                 recipeRepo.getRecipeWithTasteByKeyword(inputData.keyWord).map(mapper::execute)
            } else {
                 recipeRepo.getRecipeWithTaste().map(mapper::execute)
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