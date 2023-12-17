package com.withapp.coffeememo.create.recipe.domain.use_case

interface GetBeanCountUseCase {
    suspend fun handle(): Int
}