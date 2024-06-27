package com.withapp.coffeememo.di.search.recipe.use_case

import com.withapp.coffeememo.domain.usecase.recipe.filter.FilterRecipeIterator
import com.withapp.coffeememo.domain.usecase.recipe.filter.FilterRecipeUseCase
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