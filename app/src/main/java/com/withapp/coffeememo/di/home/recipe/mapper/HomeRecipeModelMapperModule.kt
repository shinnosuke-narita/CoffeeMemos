package com.withapp.coffeememo.di.home.recipe.mapper

import com.withapp.coffeememo.data.mapper.recipe.HomeRecipeModelMapper
import com.withapp.coffeememo.data.mapper.recipe.impl.HomeRecipeModelMapperImpl
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