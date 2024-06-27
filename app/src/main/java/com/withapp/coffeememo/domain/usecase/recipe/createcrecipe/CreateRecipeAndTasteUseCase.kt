package com.withapp.coffeememo.domain.usecase.recipe.createcrecipe

import com.withapp.coffeememo.entity.InputData

interface CreateRecipeAndTasteUseCase {
    suspend fun handle(
        inputData: InputData
    )
}