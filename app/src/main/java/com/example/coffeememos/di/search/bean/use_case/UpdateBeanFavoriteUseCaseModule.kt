package com.example.coffeememos.di.search.bean.use_case

import com.example.coffeememos.search.bean.domain.iterator.UpdateFavoriteBeanInterator
import com.example.coffeememos.search.bean.domain.use_case.UpdateFavoriteBeanUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UpdateBeanFavoriteUseCaseModule {

    @Binds
    abstract fun bindUpdateBeanFavoriteUseCase(
        updateBeanFavoriteUseCaseInteractor: UpdateFavoriteBeanInterator
    ): UpdateFavoriteBeanUseCase
}