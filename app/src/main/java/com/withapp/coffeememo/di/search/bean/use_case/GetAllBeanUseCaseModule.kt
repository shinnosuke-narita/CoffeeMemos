package com.withapp.coffeememo.di.search.bean.use_case

import com.withapp.coffeememo.search.bean.domain.interactor.GetAllBeanIterator
import com.withapp.coffeememo.search.bean.domain.use_case.GetAllBeanUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class GetAllBeanUseCaseModule {

    @Binds
    abstract fun bindGetAllBeanUseCase(
        GetAllBeanUseCaseImpl: GetAllBeanIterator
    ): GetAllBeanUseCase
}