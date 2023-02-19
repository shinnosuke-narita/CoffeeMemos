package com.example.coffeememos.search.domain.use_case

import com.example.coffeememos.search.domain.model.FilterRecipeInputData

interface SetFilterRecipeInputDataUseCase {
    fun execute(inputData: FilterRecipeInputData)
}