package com.withapp.coffeememo.domain.usecase.recipe.freewordsearch

import com.withapp.coffeememo.domain.repository.RecipeRepository
import com.withapp.coffeememo.search.recipe.data.mapper.SearchRecipeModelMapper
import com.withapp.coffeememo.search.recipe.domain.model.SearchRecipeModel
import javax.inject.Inject

class FreeWordSearchInteractor @Inject constructor(
    private val recipeRepo: RecipeRepository,
    private val mapper: SearchRecipeModelMapper
) : FreeWordSearchUseCase {
    override suspend fun handle(freeWord: String): List<SearchRecipeModel> {
        return recipeRepo.getRecipeWithTasteByKeyword(freeWord).map(mapper::execute)
    }
}