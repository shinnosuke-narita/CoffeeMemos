package com.withapp.coffeememo.di.favorite.recipe.use_case

import com.withapp.coffeememo.favorite.recipe.domain.interactor.DeleteFavoriteInteractor
import com.withapp.coffeememo.favorite.recipe.domain.use_case.DeleteFavoriteUseCase
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