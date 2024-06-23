package com.withapp.coffeememo.di.search.bean.use_case

import com.withapp.coffeememo.search.bean.domain.interactor.FilterBeanInteractor
import com.withapp.coffeememo.search.bean.domain.use_case.FilterBeanUseCase
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