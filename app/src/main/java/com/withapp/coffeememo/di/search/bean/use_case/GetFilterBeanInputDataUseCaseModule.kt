package com.withapp.coffeememo.di.search.bean.use_case

import com.withapp.coffeememo.search.bean.domain.interactor.GetFilterBeanInputDataIterator
import com.withapp.coffeememo.search.bean.domain.use_case.GetFilterBeanOutputDataUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class GetFilterBeanInputDataUseCaseModule {

    @Binds
    abstract fun bindGetFilterBeanInputDataUseCase(
        getFilterBeanInputDataImpl: GetFilterBeanInputDataIterator
    ): GetFilterBeanOutputDataUseCase
}