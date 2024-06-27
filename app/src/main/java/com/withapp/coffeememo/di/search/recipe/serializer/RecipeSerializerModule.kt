package com.withapp.coffeememo.di.search.recipe.serializer

import com.withapp.coffeememo.domain.serialization.recipe.RecipeSerializer
import com.withapp.coffeememo.domain.serialization.recipe.RecipeSerializerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RecipeSerializerModule {

    @Binds
    abstract fun bindRecipeSerializer(
        recipeSerializerImpl: RecipeSerializerImpl
    ): RecipeSerializer
}