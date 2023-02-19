package com.example.coffeememos.search.domain.use_case

import com.example.coffeememos.search.domain.model.FilterRecipeOutputData

interface GetFilterRecipeOutputDataUseCase {
     fun execute(key: String): FilterRecipeOutputData?
}