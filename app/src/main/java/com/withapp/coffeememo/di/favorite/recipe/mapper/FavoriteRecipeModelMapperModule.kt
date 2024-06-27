package com.withapp.coffeememo.di.favorite.recipe.mapper

import com.withapp.coffeememo.presentation.favorite.recipe.mapper.FavoriteRecipeModelMapper
import com.withapp.coffeememo.presentation.favorite.recipe.mapper.FavoriteRecipeModelMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class FavoriteRecipeModelMapperModule {

    @Binds
    abstract fun bindFavoriteRecipeModelMapper(
        favoriteRecipeModelMapperImpl: FavoriteRecipeModelMapperImpl
    ): FavoriteRecipeModelMapper
}