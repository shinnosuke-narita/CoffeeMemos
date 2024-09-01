package com.withapp.coffeememo.domain.usecase.recipe.filter

import com.withapp.coffeememo.domain.repository.RecipeRepository
import com.withapp.coffeememo.domain.mapper.SearchRecipeModelMapper
import com.withapp.coffeememo.domain.model.recipe.FilterRecipeInputData
import com.withapp.coffeememo.domain.model.recipe.SearchRecipeModel
import javax.inject.Inject

class FilterRecipeInteractor @Inject constructor(
    private val recipeRepo: RecipeRepository,
    private val mapper: SearchRecipeModelMapper
) : FilterRecipeUseCase {
    override suspend fun filterRecipe(inputData: FilterRecipeInputData): List<SearchRecipeModel> {
        val recipes: List<SearchRecipeModel> =
            if (inputData.keyWord.isNotEmpty()) {
                 recipeRepo.getRecipeWithTasteByKeyword(inputData.keyWord).map(mapper::execute)
            } else {
                 recipeRepo.getRecipeWithTaste().map(mapper::execute)
            }

        if (recipes.isEmpty()) return recipes

        // 酸味
        var result = getMatchedRecipes(recipes, inputData.sour) {
                recipe, value -> recipe.sour == value }
        if (result.isEmpty())  return result

        // 苦味
        result = getMatchedRecipes(result, inputData.bitter) {
            recipe, data -> recipe.bitter == data
        }
        if (result.isEmpty()) return result

        // 甘味
        result = getMatchedRecipes(result, inputData.sweet) {
                recipe, data -> recipe.sweet == data
        }
        if (result.isEmpty()) return result

        // 香り
        result = getMatchedRecipes(result, inputData.flavor) {
                recipe, data -> recipe.flavor == data
        }
        if (result.isEmpty()) return result

        // コク
        result = getMatchedRecipes(result, inputData.rich) {
                recipe, data -> recipe.rich == data
        }
        if (result.isEmpty()) return result
        // 評価
        result = getMatchedRecipes(result, inputData.rating) {
                recipe, data -> recipe.rating == data
        }
        if (result.isEmpty()) return result

        // 焙煎度
        result = getMatchedRecipes(result, inputData.roasts) {
                recipe, data -> recipe.roast == data
        }
        if (result.isEmpty()) return result

        // 粒度
        result = getMatchedRecipes(result, inputData.grindSizes) {
                recipe, data -> recipe.grindSize == data
        }
        if (result.isEmpty()) return result

        // 原産地
        result = getMatchedRecipes(result, inputData.countries) {
                recipe, data -> recipe.country.contains(data)
        }
        if (result.isEmpty()) return result

        // 抽出器具
        result = getMatchedRecipes(result, inputData.tools) {
                recipe, data -> recipe.tool.contains(data)
        }

        return result
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