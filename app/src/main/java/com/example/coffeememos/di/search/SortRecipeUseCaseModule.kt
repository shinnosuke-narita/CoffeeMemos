package com.example.coffeememos.di.search

import com.example.coffeememos.search.data.repository.SearchRecipeRepositoryImpl
import com.example.coffeememos.search.domain.interator.SortRecipeInteractor
import com.example.coffeememos.search.domain.repository.SearchRecipeRepository
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