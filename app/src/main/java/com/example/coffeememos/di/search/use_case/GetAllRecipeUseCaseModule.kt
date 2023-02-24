package com.example.coffeememos.di.search.use_case

import com.example.coffeememos.search.recipe.domain.iterator.GetAllRecipeIterator
import com.example.coffeememos.search.recipe.domain.use_case.GetAllRecipeUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class GetAllRecipeUseCaseModule {

    @Binds
    abstract fun bindGetAllRecipeUseCase(
        GetAllRecipeUseCaseImpl: GetAllRecipeIterator
    ): GetAllRecipeUseCase
}