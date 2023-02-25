package com.example.coffeememos.di.search.recipe.use_case

import com.example.coffeememos.search.bean.domain.iterator.FilterBeanIterator
import com.example.coffeememos.search.bean.domain.use_case.FilterBeanUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class FilterSearchBeanUseCaseModule {

    @Binds
    abstract fun bindFilterSearchBeanUseCase(
        filterSearchUseCaseImpl: FilterBeanIterator
    ): FilterBeanUseCase
}