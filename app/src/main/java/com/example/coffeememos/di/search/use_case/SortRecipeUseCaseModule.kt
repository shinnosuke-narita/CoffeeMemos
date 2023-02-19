package com.example.coffeememos.di.search.use_case

import com.example.coffeememos.search.domain.iterator.SortRecipeInteractor
import com.example.coffeememos.search.domain.use_case.SortRecipeUseCase
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