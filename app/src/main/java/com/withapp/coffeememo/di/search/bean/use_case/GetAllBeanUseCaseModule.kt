package com.withapp.coffeememo.di.search.bean.use_case

import com.withapp.coffeememo.domain.usecase.bean.getall.GetAllBeanInteractor
import com.withapp.coffeememo.domain.usecase.bean.getall.GetAllBeanUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class GetAllBeanUseCaseModule {

    @Binds
    abstract fun bindGetAllBeanUseCase(
        GetAllBeanUseCaseImpl: GetAllBeanInteractor
    ): GetAllBeanUseCase
}