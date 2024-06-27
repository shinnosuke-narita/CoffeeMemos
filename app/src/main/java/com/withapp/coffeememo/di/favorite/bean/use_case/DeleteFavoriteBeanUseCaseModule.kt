package com.withapp.coffeememo.di.favorite.bean.use_case


import com.withapp.coffeememo.domain.usecase.bean.deletefavorite.DeleteFavoriteInteractor
import com.withapp.coffeememo.domain.usecase.bean.deletefavorite.DeleteFavoriteUseCase
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