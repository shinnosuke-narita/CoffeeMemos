package com.example.coffeememos.di.search.bean.use_case

import com.example.coffeememos.search.bean.domain.iterator.SetFilterBeanInputDataIterator
import com.example.coffeememos.search.bean.domain.use_case.SetFilterBeanInputDataUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SetFilterBeanInputDataUseCaseModule {

    @Binds
    abstract fun bindSetFilterBeanInputDataUseCase(
        setFilterBeanInputDataImpl: SetFilterBeanInputDataIterator
    ): SetFilterBeanInputDataUseCase
}