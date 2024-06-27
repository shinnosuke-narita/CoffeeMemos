package com.withapp.coffeememo.domain.usecase.recipe.getfilterelement

import com.withapp.coffeememo.domain.model.recipe.FilterRecipeOutputData

interface GetFilterRecipeOutputDataUseCase {
     fun execute(key: String): FilterRecipeOutputData?
}