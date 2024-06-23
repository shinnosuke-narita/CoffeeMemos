package com.withapp.coffeememo.di.search.bean.use_case

import com.withapp.coffeememo.search.bean.domain.interactor.SortBeanInteractor
import com.withapp.coffeememo.search.bean.domain.use_case.SortBeanUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SortBeanUseCaseModule {

    @Binds
    abstract fun bindSortBeanUse(
        sortBeanUseCaseImpl: SortBeanInteractor
    ): SortBeanUseCase
}