package com.withapp.coffeememo.di.search.recipe.use_case

import com.withapp.coffeememo.search.recipe.domain.interactor.GetFilterRecipeInputDataIterator
import com.withapp.coffeememo.search.recipe.domain.use_case.GetFilterRecipeOutputDataUseCase
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