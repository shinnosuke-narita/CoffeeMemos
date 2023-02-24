package com.example.coffeememos.search.recipe.domain.use_case

import com.example.coffeememos.search.recipe.domain.model.FilterRecipeOutputData

interface GetFilterRecipeOutputDataUseCase {
     fun execute(key: String): FilterRecipeOutputData?
}