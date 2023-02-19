package com.example.coffeememos.di.search.use_case

import com.example.coffeememos.search.domain.iterator.FilterRecipeIterator
import com.example.coffeememos.search.domain.use_case.FilterRecipeUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class FilterSearchRecipeUseCaseModule {

    @Binds
    abstract fun bindFilterSearchRecipeUseCase(
        filterSearchUseCaseImpl: FilterRecipeIterator
    ): FilterRecipeUseCase
}