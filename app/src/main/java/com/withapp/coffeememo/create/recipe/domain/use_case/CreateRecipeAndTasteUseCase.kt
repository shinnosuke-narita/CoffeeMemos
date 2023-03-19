package com.withapp.coffeememo.create.recipe.domain.use_case

import com.withapp.coffeememo.create.recipe.domain.model.InputData

interface CreateRecipeAndTasteUseCase {
    suspend fun handle(
        inputData: InputData
    )
}