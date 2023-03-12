package com.example.coffeememos.create.recipe.domain.use_case

import com.example.coffeememos.create.recipe.domain.model.InputData

interface CreateRecipeAndTasteUseCase {
    suspend fun handle(
        inputData: InputData
    )
}