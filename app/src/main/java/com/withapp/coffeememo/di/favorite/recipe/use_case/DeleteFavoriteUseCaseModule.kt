package com.withapp.coffeememo.di.favorite.recipe.use_case

import com.withapp.coffeememo.domain.usecase.recipe.deletefavorite.DeleteFavoriteInteractor
import com.withapp.coffeememo.domain.usecase.recipe.deletefavorite.DeleteFavoriteUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DeleteFavoriteUseCaseModule {

    @Binds
    abstract fun bindDeleteFavoriteUseCase(
        deleteFavoriteInteractor: DeleteFavoriteInteractor
    ): DeleteFavoriteUseCase
}