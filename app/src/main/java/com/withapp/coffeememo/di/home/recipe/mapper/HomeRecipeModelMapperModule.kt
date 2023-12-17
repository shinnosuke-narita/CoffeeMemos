package com.withapp.coffeememo.di.home.recipe.mapper

import com.withapp.coffeememo.home.recipe.data.mapper.HomeRecipeModelMapper
import com.withapp.coffeememo.home.recipe.data.mapper.HomeRecipeModelMapperImpl
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