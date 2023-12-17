package com.withapp.coffeememo.search.recipe.domain.use_case

import com.withapp.coffeememo.search.recipe.domain.model.FilterRecipeInputData

interface SetFilterRecipeInputDataUseCase {
    fun execute(inputData: FilterRecipeInputData)
}