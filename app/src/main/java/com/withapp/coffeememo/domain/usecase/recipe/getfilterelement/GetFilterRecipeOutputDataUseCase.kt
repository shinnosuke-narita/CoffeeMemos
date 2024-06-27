package com.withapp.coffeememo.domain.usecase.recipe.getfilterelement

import com.withapp.coffeememo.search.recipe.domain.model.FilterRecipeOutputData

interface GetFilterRecipeOutputDataUseCase {
     fun execute(key: String): FilterRecipeOutputData?
}