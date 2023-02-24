package com.example.coffeememos.di.search.use_case

import com.example.coffeememos.search.recipe.domain.iterator.SetFilterRecipeInputDataIterator
import com.example.coffeememos.search.recipe.domain.use_case.SetFilterRecipeInputDataUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SetFilterRecipeInputDataUseCaseModule {

    @Binds
    abstract fun bindSetFilterRecipeInputDataUseCase(
        setFilterRecipeInputDataImpl: SetFilterRecipeInputDataIterator
    ): SetFilterRecipeInputDataUseCase
}