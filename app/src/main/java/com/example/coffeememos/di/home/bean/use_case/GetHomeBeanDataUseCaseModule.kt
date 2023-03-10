package com.example.coffeememos.di.home.bean.use_case

import com.example.coffeememos.home.bean.domain.interactor.GetHomeBeanDataUseCaseInteractor
import com.example.coffeememos.home.bean.domain.use_case.GetHomeBeanDataUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class GetHomeBeanDataUseCaseModule {

    @Binds
    abstract fun bindGetHomeBeanUseCase(
        getHomeBeanDataUseCaseImpl: GetHomeBeanDataUseCaseInteractor
    ): GetHomeBeanDataUseCase
}