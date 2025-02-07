package com.withapp.coffeememo.di.repository

import com.withapp.coffeememo.data.repository.bean.BeanRepositoryImpl
import com.withapp.coffeememo.data.repository.recipe.RecipeRepositoryImpl
import com.withapp.coffeememo.data.repository.taste.TasteRepositoryImpl
import com.withapp.coffeememo.domain.repository.BeanRepository
import com.withapp.coffeememo.domain.repository.RecipeRepository
import com.withapp.coffeememo.domain.repository.TasteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRecipeRepository(
        recipeRepositoryImpl: RecipeRepositoryImpl
    ): RecipeRepository

    @Binds
    abstract fun bindBeanRepository(
        beanRepositoryImpl: BeanRepositoryImpl
    ): BeanRepository

    @Binds
    abstract fun bindTasteRepository(
        tasteRepositoryImpl: TasteRepositoryImpl
    ): TasteRepository
}