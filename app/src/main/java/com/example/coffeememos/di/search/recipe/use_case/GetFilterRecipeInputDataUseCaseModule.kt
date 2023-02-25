package com.example.coffeememos.di.search.recipe.use_case

import com.example.coffeememos.search.recipe.domain.iterator.GetFilterRecipeInputDataIterator
import com.example.coffeememos.search.recipe.domain.use_case.GetFilterRecipeOutputDataUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class GetFilterRecipeInputDataUseCaseModule {

    @Binds
    abstract fun bindGetFilterRecipeInputDataUseCase(
        getFilterRecipeInputDataImpl: GetFilterRecipeInputDataIterator
    ): GetFilterRecipeOutputDataUseCase
}