package com.withapp.coffeememo.search.recipe.domain.use_case

import com.withapp.coffeememo.search.recipe.domain.model.FilterRecipeOutputData

interface GetFilterRecipeOutputDataUseCase {
     fun execute(key: String): FilterRecipeOutputData?
}