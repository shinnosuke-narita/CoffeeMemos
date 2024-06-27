package com.withapp.coffeememo.di.favorite.bean.mapper


import com.withapp.coffeememo.presentation.favorite.bean.mapper.FavoriteBeanModelMapper
import com.withapp.coffeememo.presentation.favorite.bean.mapper.FavoriteBeanModelMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class FavoriteBeanModelMapperModule {

    @Binds
    abstract fun bindFavoriteBeanModelMapper(
        favoriteBeanModelMapperImpl: FavoriteBeanModelMapperImpl
    ): FavoriteBeanModelMapper
}