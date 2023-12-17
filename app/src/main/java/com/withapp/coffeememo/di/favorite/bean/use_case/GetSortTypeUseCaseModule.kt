package com.withapp.coffeememo.di.favorite.bean.use_case


import com.withapp.coffeememo.favorite.bean.domain.interactor.GetSortTypeInteractor
import com.withapp.coffeememo.favorite.bean.domain.use_case.GetSortTypeUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class GetSortTypeUseCaseModule {

    @Binds
    abstract fun bindGetSortTypeUseCase(
        getSortTypeUseCaseImpl: GetSortTypeInteractor
    ): GetSortTypeUseCase
}