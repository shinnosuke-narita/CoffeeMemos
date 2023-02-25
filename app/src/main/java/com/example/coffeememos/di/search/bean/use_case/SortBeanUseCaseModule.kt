package com.example.coffeememos.di.search.bean.use_case

import com.example.coffeememos.search.bean.domain.iterator.SortBeanInteractor
import com.example.coffeememos.search.bean.domain.use_case.SortBeanUseCase
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