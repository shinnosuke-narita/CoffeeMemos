package com.withapp.coffeememo.di.favorite.recipe.use_case

import com.withapp.coffeememo.favorite.recipe.domain.interactor.GetSortTypeInteractor
import com.withapp.coffeememo.favorite.recipe.domain.use_case.GetSortTypeUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class GetSortTypeUseCaseModule {

    @Binds
    abstract fun bindGetSortTypeUseCase(
        getSortTypeUseCaseImpl: GetSortTypeInteractor
    ): GetSortTypeUseCase
}