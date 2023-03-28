package com.withapp.coffeememo.di.favorite.bean.use_case


import com.withapp.coffeememo.favorite.bean.domain.interactor.GetSortDialogDataInteractor
import com.withapp.coffeememo.favorite.bean.domain.use_case.GetSortDialogDataUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class GetSortDialogDataUseCaseModule {

    @Binds
    abstract fun bindGetSortDialogDataUseCase(
        getSortDialogDataUseCaseImpl: GetSortDialogDataInteractor
    ): GetSortDialogDataUseCase
}