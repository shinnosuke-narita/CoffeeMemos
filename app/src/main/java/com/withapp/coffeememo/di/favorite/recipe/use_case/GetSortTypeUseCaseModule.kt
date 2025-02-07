package com.withapp.coffeememo.di.favorite.recipe.use_case

import com.withapp.coffeememo.domain.usecase.recipe.getsorttype.GetSortTypeInteractor
import com.withapp.coffeememo.domain.usecase.recipe.getsorttype.GetSortTypeUseCase
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