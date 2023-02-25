package com.example.coffeememos.di.search.recipe.use_case

import com.example.coffeememos.search.recipe.domain.iterator.FreeWordSearchIterator
import com.example.coffeememos.search.recipe.domain.use_case.FreeWordSearchUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class FreeWordSearchRecipeUseCaseModule {

    @Binds
    abstract fun bindFreeWordSearchRecipeUseCase(
        freeWordSearchUseCaseImpl: FreeWordSearchIterator
    ): FreeWordSearchUseCase
}