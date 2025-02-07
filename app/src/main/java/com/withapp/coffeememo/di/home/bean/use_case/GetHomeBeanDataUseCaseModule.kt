package com.withapp.coffeememo.di.home.bean.use_case

import com.withapp.coffeememo.domain.usecase.bean.gethomebean.GetHomeBeanDataUseCaseInteractor
import com.withapp.coffeememo.domain.usecase.bean.gethomebean.GetHomeBeanDataUseCase
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