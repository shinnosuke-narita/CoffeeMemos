package com.withapp.coffeememo.di.search.bean.use_case

import com.withapp.coffeememo.domain.usecase.bean.filter.FilterBeanInteractor
import com.withapp.coffeememo.domain.usecase.bean.filter.FilterBeanUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class FilterSearchBeanUseCaseModule {

    @Binds
    abstract fun bindFilterSearchBeanUseCase(
        filterSearchUseCaseImpl: FilterBeanInteractor
    ): FilterBeanUseCase
}