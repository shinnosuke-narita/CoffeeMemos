package com.withapp.coffeememo.di.favorite.recipe.use_case

import com.withapp.coffeememo.domain.usecase.recipe.getfavoriterecipe.GetFavoriteRecipeInteractor
import com.withapp.coffeememo.domain.usecase.recipe.getfavoriterecipe.GetFavoriteRecipeUseCase
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