package com.withapp.coffeememo.di.favorite.bean.use_case


import com.withapp.coffeememo.favorite.bean.domain.interactor.DeleteFavoriteInteractor
import com.withapp.coffeememo.favorite.bean.domain.use_case.DeleteFavoriteUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DeleteFavoriteBeanUseCaseModule {

    @Binds
    abstract fun bindDeleteFavoriteUseCase(
        deleteFavoriteUseCaseImpl: DeleteFavoriteInteractor
    ): DeleteFavoriteUseCase
}