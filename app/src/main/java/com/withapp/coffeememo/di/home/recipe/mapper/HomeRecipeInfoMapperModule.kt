package com.withapp.coffeememo.di.home.recipe.mapper

import com.withapp.coffeememo.presentation.home.recipe.mapper.HomeRecipeCardModelMapper
import com.withapp.coffeememo.presentation.home.recipe.mapper.HomeRecipeCardModelMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeRecipeInfoMapperModule {

    @Binds
    abstract fun bindHomeRecipeOutPutMapper(
        homeRecipeOutPutMapperImpl: HomeRecipeCardModelMapperImpl
    ): HomeRecipeCardModelMapper
}