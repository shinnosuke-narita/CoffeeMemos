package com.withapp.coffeememo.di.search.bean.use_case

import com.withapp.coffeememo.search.bean.domain.interactor.SetFilterBeanInputDataIterator
import com.withapp.coffeememo.search.bean.domain.use_case.SetFilterBeanInputDataUseCase
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