package com.withapp.coffeememo.domain.usecase.recipe.savefilterelement

import com.withapp.coffeememo.search.recipe.domain.model.FilterRecipeInputData

interface SetFilterRecipeInputDataUseCase {
    fun execute(inputData: FilterRecipeInputData)
}