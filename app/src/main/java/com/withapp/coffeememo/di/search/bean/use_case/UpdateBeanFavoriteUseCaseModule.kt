package com.withapp.coffeememo.di.search.bean.use_case

import com.withapp.coffeememo.domain.usecase.bean.updatefavorite.UpdateFavoriteBeanInterator
import com.withapp.coffeememo.domain.usecase.bean.updatefavorite.UpdateFavoriteBeanUseCase
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