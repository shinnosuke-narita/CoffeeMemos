package com.withapp.coffeememo.di.search.recipe.use_case

import com.withapp.coffeememo.search.recipe.domain.interactor.SetFilterRecipeInputDataIterator
import com.withapp.coffeememo.search.recipe.domain.use_case.SetFilterRecipeInputDataUseCase
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