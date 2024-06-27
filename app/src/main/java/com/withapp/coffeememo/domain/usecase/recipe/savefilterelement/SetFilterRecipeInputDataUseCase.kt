package com.withapp.coffeememo.domain.usecase.recipe.savefilterelement

import com.withapp.coffeememo.domain.model.recipe.FilterRecipeInputData

interface SetFilterRecipeInputDataUseCase {
    fun execute(inputData: FilterRecipeInputData)
}