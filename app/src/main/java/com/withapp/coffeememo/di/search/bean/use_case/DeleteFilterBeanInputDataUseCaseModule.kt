package com.withapp.coffeememo.di.search.bean.use_case

import com.withapp.coffeememo.domain.usecase.bean.deletefilterelement.DeleteFilterBeanInputDataIterator
import com.withapp.coffeememo.domain.usecase.bean.deletefilterelement.DeleteFilterBeanInputDataUseCase
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