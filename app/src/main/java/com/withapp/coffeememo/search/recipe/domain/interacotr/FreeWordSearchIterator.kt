package com.withapp.coffeememo.search.recipe.domain.interacotr

import com.withapp.coffeememo.domain.repository.RecipeRepository
import com.withapp.coffeememo.search.recipe.data.mapper.SearchRecipeModelMapper
import com.withapp.coffeememo.search.recipe.domain.model.SearchRecipeModel
import com.withapp.coffeememo.search.recipe.domain.use_case.FreeWordSearchUseCase
import javax.inject.Inject

class FreeWordSearchIterator @Inject constructor(
    private val recipeRepo: RecipeRepository,
    private val mapper: SearchRecipeModelMapper
) : FreeWordSearchUseCase {
    override suspend fun handle(freeWord: String): List<SearchRecipeModel> {
        return recipeRepo.getRecipeWithTasteByKeyword(freeWord).map(mapper::execute)
    }
}