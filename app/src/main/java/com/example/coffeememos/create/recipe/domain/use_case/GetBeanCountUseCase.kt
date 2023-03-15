package com.example.coffeememos.create.recipe.domain.use_case

interface GetBeanCountUseCase {
    suspend fun handle(): Int
}