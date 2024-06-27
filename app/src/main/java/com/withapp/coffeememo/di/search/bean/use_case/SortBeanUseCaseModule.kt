package com.withapp.coffeememo.di.search.bean.use_case

import com.withapp.coffeememo.domain.usecase.bean.sort.SortBeanInteractor
import com.withapp.coffeememo.domain.usecase.bean.sort.SortBeanUseCase
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