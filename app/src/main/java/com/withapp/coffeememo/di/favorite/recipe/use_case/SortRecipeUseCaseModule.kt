package com.withapp.coffeememo.di.favorite.recipe.use_case

import com.withapp.coffeememo.favorite.recipe.domain.interactor.SortRecipeInteractor
import com.withapp.coffeememo.favorite.recipe.domain.use_case.SortRecipeUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SortRecipeUseCaseModule {

    @Binds
    abstract fun bindSortRecipeUseCase(
        sortRecipeUseCaseImpl: SortRecipeInteractor
    ): SortRecipeUseCase
}