package com.example.coffeememos.di.search.bean.use_case

import com.example.coffeememos.search.bean.domain.iterator.DeleteFilterBeanInputDataIterator
import com.example.coffeememos.search.bean.domain.use_case.DeleteFilterBeanInputDataUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DeleteFilterBeanInputDataUseCaseModule {

    @Binds
    abstract fun bindDeleteFilterBeanInputDataUseCase(
        deleteFilterBeanInputData: DeleteFilterBeanInputDataIterator
    ): DeleteFilterBeanInputDataUseCase
}