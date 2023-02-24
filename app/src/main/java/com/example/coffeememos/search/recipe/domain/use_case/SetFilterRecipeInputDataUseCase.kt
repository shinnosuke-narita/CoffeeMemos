package com.example.coffeememos.search.recipe.domain.use_case

import com.example.coffeememos.search.recipe.domain.model.FilterRecipeInputData

interface SetFilterRecipeInputDataUseCase {
    fun execute(inputData: FilterRecipeInputData)
}