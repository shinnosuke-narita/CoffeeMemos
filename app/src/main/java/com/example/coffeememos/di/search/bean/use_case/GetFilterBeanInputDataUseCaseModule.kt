package com.example.coffeememos.di.search.bean.use_case

import com.example.coffeememos.search.bean.domain.iterator.GetFilterBeanInputDataIterator
import com.example.coffeememos.search.bean.domain.use_case.GetFilterBeanOutputDataUseCase
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