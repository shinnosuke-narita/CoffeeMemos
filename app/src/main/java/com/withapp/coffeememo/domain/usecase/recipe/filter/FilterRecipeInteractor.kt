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
        val recipes =
            if (inputData.keyWord.isNotEmpty()) {
                 recipeRepo.getRecipeWithTasteByKeyword(inputData.keyWord)
            } else {
                 recipeRepo.getRecipeWithTaste()
            }

        return recipes.asSequence()
            .map(mapper::execute)
            .filter { recipe -> filteringPredicate(recipe.sour, inputData.sour) }
            .filter { recipe -> filteringPredicate(recipe.bitter, inputData.bitter) }
            .filter { recipe -> filteringPredicate(recipe.sweet, inputData.sweet) }
            .filter { recipe -> filteringPredicate(recipe.flavor, inputData.flavor) }
            .filter { recipe -> filteringPredicate(recipe.rich, inputData.rich) }
            .filter { recipe -> filteringPredicate(recipe.rating, inputData.rating) }
            .filter { recipe -> filteringPredicate(recipe.grindSize, inputData.grindSizes) }
            .filter { recipe -> filteringPredicate(recipe.country, inputData.countries) }
            .filter { recipe -> filteringPredicate(recipe.tool, inputData.tools) }
            .toList()
    }

    private fun <T> filteringPredicate(target: T, filteringValue: List<T>): Boolean =
        if (filteringValue.isEmpty()) true else filteringValue.contains(target)
}