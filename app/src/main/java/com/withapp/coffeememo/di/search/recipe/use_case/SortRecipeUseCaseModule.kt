package com.withapp.coffeememo.di.search.recipe.use_case

import com.withapp.coffeememo.search.recipe.domain.iterator.SortRecipeInteractor
import com.withapp.coffeememo.search.recipe.domain.use_case.SortRecipeUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SortRecipeUseCaseModule {

    @Binds
    abstract fun bindSortRecipeUse(
        sortRecipeUseCaseImpl: SortRecipeInteractor
    ): SortRecipeUseCase
}