package com.example.coffeememos.di.home.recipe.mapper

import com.example.coffeememos.home.recipe.presentation.mapper.HomeRecipeInfoMapper
import com.example.coffeememos.home.recipe.presentation.mapper.HomeRecipeInfoMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeRecipeInfoMapperModule {

    @Binds
    abstract fun bindHomeRecipeOutPutMapper(
        homeRecipeOutPutMapperImpl: HomeRecipeInfoMapperImpl
    ): HomeRecipeInfoMapper
}