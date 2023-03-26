package com.withapp.coffeememo.di.favorite.recipe.use_case

import com.withapp.coffeememo.favorite.recipe.domain.interactor.GetSortDialogDataInteractor
import com.withapp.coffeememo.favorite.recipe.domain.use_case.GetSortDialogDataUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class GetSortDialogDataUseCaseModule {

    @Binds
    abstract fun bindGetSortDialogDataUseCase(
        getSortDialogDataUseCaseImpl: GetSortDialogDataInteractor
    ): GetSortDialogDataUseCase
}