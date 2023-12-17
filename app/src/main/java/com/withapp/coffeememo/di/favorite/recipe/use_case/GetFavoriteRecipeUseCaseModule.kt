package com.withapp.coffeememo.di.favorite.recipe.use_case

import com.withapp.coffeememo.favorite.recipe.domain.interactor.GetFavoriteRecipeInteractor
import com.withapp.coffeememo.favorite.recipe.domain.use_case.GetFavoriteRecipeUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class GetFavoriteRecipeUseCaseModule {

    @Binds
    abstract fun bindFavoriteRecipeUseCase(
        getFavoriteRecipeUseCase: GetFavoriteRecipeInteractor
    ): GetFavoriteRecipeUseCase
}