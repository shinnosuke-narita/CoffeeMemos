package com.example.coffeememos.di.home.recipe.mapper

import com.example.coffeememos.home.recipe.data.mapper.HomeRecipeModelMapper
import com.example.coffeememos.home.recipe.data.mapper.HomeRecipeModelMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeRecipeModelMapperModule {

    @Binds
    abstract fun bindHomeRecipeModelMapper(
        homeRecipeModelMapper: HomeRecipeModelMapperImpl
    ): HomeRecipeModelMapper
}