package com.withapp.coffeememo.di.favorite.bean.use_case


import com.withapp.coffeememo.domain.usecase.bean.sortbean.SortBeanInteractor
import com.withapp.coffeememo.domain.usecase.bean.sortbean.SortBeanUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SortBeanUseCaseModule {

    @Binds
    abstract fun bindSortBeanUseCase(
        sortBeanUseCaseImpl: SortBeanInteractor
    ): SortBeanUseCase
}